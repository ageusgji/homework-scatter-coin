package com.ageusgji.toy.kakaoPay.coinScatter.service;

import com.ageusgji.toy.kakaoPay.coinScatter.domain.ReceiveRepository;
import com.ageusgji.toy.kakaoPay.coinScatter.domain.Receiver;
import com.ageusgji.toy.kakaoPay.coinScatter.domain.ScatterRepository;
import com.ageusgji.toy.kakaoPay.coinScatter.domain.Scatterer;
import com.ageusgji.toy.kakaoPay.coinScatter.dto.DisplayDTO;
import com.ageusgji.toy.kakaoPay.coinScatter.dto.SingleDTO;
import com.ageusgji.toy.kakaoPay.coinScatter.util.enums.CoinScatterExceptionType;
import com.ageusgji.toy.kakaoPay.coinScatter.util.exception.CoinScatterException;
import com.ageusgji.toy.kakaoPay.coinScatter.web.CommonResponse;
import com.ageusgji.toy.kakaoPay.coinScatter.web.ReceiveRequest;
import com.ageusgji.toy.kakaoPay.coinScatter.web.ScatterRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CoinScatterService {
    @Autowired
    private ScatterRepository scatterRepository;

    @Autowired
    private ReceiveRepository receiveRepository;

    /**
     * https://www.baeldung.com/java-random-string
     *
     * @param scatterRequest
     * @return randomStr
     */
    public CommonResponse scatter(ScatterRequest scatterRequest) {

        String token = generateToken(scatterRequest.getRoomId());

        // 개별 받게 될 금액 : eachCoin = ( totalCoin / headCount )
        Long eachCoin = scatterRequest.getTotalCoin() / scatterRequest.getHeadCount();

        // 보너스 금 계산 : receiver.bonusCoin = totalCoin - ( eachCoin * headCount )
        Long bonusCoin = scatterRequest.getTotalCoin() - ( eachCoin * scatterRequest.getHeadCount() );

        LocalDateTime currentDateTime = LocalDateTime.now();
        currentDateTime = currentDateTime.plusMinutes(10);

        Scatterer scatterer = Scatterer.builder()
                .userNo(scatterRequest.getUserNo())
                .roomId(scatterRequest.getRoomId())
                .headCount(scatterRequest.getHeadCount())
                .totalCoin(scatterRequest.getTotalCoin())
                .eachCoin(eachCoin)
                .bonusCoin(bonusCoin)
                .expireDateTime(currentDateTime)
                .token(token)
                .remainCoin(scatterRequest.getTotalCoin())
                .receiverCount(0)
                .build();

        scatterRepository.saveAndFlush(scatterer);

        return CommonResponse.success(SingleDTO.token(token));
    }


    private String generateToken(String roomId) {

        LocalDateTime startAt = LocalDateTime.now();
        LocalDateTime limitAt = LocalDateTime.now().plusSeconds(5);

        while (LocalDateTime.now().isBefore(limitAt)) {

            int leftLimit = 48; // numeral '0'
            int rightLimit = 122; // letter 'z'
            SecureRandom random = new SecureRandom();
            long targetStringLength = 3;
            String randomStr = random.ints(leftLimit, rightLimit)
                    .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                    .limit(targetStringLength)
                    .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                    .toString();

            Boolean notFound = scatterRepository.findAllByRoomIdAndToken(roomId, randomStr)
                    .isEmpty();

            if (notFound) return randomStr;
        }
        // TODO: throw exception!!! - 토큰 오래 만듬! 타임아웃!!
        throw new CoinScatterException(CoinScatterExceptionType.TIMEOUT_GEN_TOKEN);

    }


    public CommonResponse receive(ReceiveRequest receiveRequest) {
        LocalDateTime now = LocalDateTime.now();
        Long receiveUserNo = receiveRequest.getUserNo();

        // 조회 : 뿌리기 건( 대화방 + 토큰 )
        List<Scatterer> scatterers = scatterRepository
                .findAllByRoomIdAndToken(receiveRequest.getRoomId(),receiveRequest.getToken())
                .stream().filter(a -> now.isBefore(a.getExpireDateTime()))
                .collect(Collectors.toList());

        // 체크 : 뿌리기 건 유무, [Error]뿌리기 건 중복
        if (scatterers.isEmpty()) {
            // TODO: throw exception!!! - 해당 토큰이 없다!!
            throw new CoinScatterException(CoinScatterExceptionType.NOT_FOUND_TOKEN);

        }else if (scatterers.size() > 1){
            // TODO: throw exception!!! - 토큰이 너무 많다~!! 생성시 문제가 있는것으로 판단!!
            throw new CoinScatterException(CoinScatterExceptionType.TOO_MANY_TOKEN_FATAL);
        }

        Scatterer targetScatterer = scatterers.get(0);

        // Validate : 뿌리기 한 사람이 받는가?
        if (targetScatterer.getUserNo().equals(receiveUserNo)){
            // TODO: throw logical exception!!! - 뿌린사람이 또 받아?
            throw new CoinScatterException(CoinScatterExceptionType.SCATTER_OWNER);
        }

        List<Receiver> receivers = receiveRepository
                .findAllByScattererId(targetScatterer.getId());

        // Validate : 모든인원이 받았는가?
        if(targetScatterer.getHeadCount().compareTo(receivers.size()) <= 0 ){
            // TODO: throw logical exception!!! - 모든 사람이 다 받았다!!
            throw new CoinScatterException(CoinScatterExceptionType.NO_COIN_LEFT);
        }


        // Validate : 받은사람이 또 받았는가?
        if (receivers.stream().anyMatch(r-> r.getUserNo().equals(receiveUserNo))){
            // TODO: throw logical exception!!! - 한번 받은사람이 또 받을 수 없습니다.
            throw new CoinScatterException(CoinScatterExceptionType.NOT_RECEIVE_AGAIN);
        }

        // 몇번째 차례인가? 마지막 차례이면 보너스 코인을 가져온다.
        Integer thisTurn = receivers.size() + 1;
        Long bonusCoin = 0L;
        if(thisTurn.compareTo(targetScatterer.getHeadCount()) == 0 ){
            bonusCoin = targetScatterer.getBonusCoin();
        }
        Long receivedCoin = targetScatterer.getEachCoin() + bonusCoin;

        // 받기 처리 (receiver) : insert
        Receiver newReceiver = Receiver.builder()
                .scattererId(targetScatterer.getId())
                .userNo(receiveUserNo)
                .turn(thisTurn)
                .coin(receivedCoin)
                .build();

        receiveRepository.save(newReceiver);

        // 받기 처리 (scatterer) : update
        // scatterer.receiverCount = [update]
        // scatterer.remainCoin = [update] totalCoin - ( eachCoin * thisTurn[receiverCount] )
        targetScatterer.setReceiverCount(thisTurn);
        targetScatterer.setRemainCoin(targetScatterer.getRemainCoin() - (targetScatterer.getEachCoin() + bonusCoin));

        scatterRepository.save(targetScatterer);

        return CommonResponse.success(SingleDTO.coin(receivedCoin));
    }



    public CommonResponse display(ReceiveRequest receiveRequest) {

        // 조회 : 뿌리기 건( 대화방 + 토큰 )
        Scatterer scatterer = scatterRepository
                .findAllByRoomIdAndToken(receiveRequest.getRoomId(), receiveRequest.getToken())
                .stream().findFirst().orElseThrow(()->{
                    // TODO: throw logical exception!!! - 찾았는데 없음.
                    throw new CoinScatterException(CoinScatterExceptionType.NOT_FOUNT_SCATTER);
                });

        List<Receiver> receivers = receiveRepository
                .findAllByScattererId(scatterer.getId());

        return CommonResponse.success(new DisplayDTO(scatterer, receivers));
    }

}
