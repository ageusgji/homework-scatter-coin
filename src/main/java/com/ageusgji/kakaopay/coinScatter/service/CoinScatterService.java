package com.ageusgji.kakaopay.coinScatter.service;

import com.ageusgji.kakaopay.coinScatter.domain.entitiy.Receiver;
import com.ageusgji.kakaopay.coinScatter.domain.entitiy.Scatterer;
import com.ageusgji.kakaopay.coinScatter.domain.repository.ReceiverRepository;
import com.ageusgji.kakaopay.coinScatter.domain.repository.ScattererRepository;
import com.ageusgji.kakaopay.coinScatter.dto.CoinDTO;
import com.ageusgji.kakaopay.coinScatter.dto.DisplayDTO;
import com.ageusgji.kakaopay.coinScatter.dto.TokenDTO;
import com.ageusgji.kakaopay.coinScatter.util.enums.CoinScatterExceptionType;
import com.ageusgji.kakaopay.coinScatter.util.exception.CoinScatterException;
import com.ageusgji.kakaopay.coinScatter.web.CommonResponse;
import com.ageusgji.kakaopay.coinScatter.web.ReceiveRequest;
import com.ageusgji.kakaopay.coinScatter.web.ScatterRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CoinScatterService {
    @Autowired
    private ScattererRepository scattererRepository;

    @Autowired
    private ReceiverRepository receiverRepository;

    public CommonResponse scatter(ScatterRequest scatterRequest) {
        return scatter(scatterRequest, 60);

    }
        /**
         * https://www.baeldung.com/java-random-string
         *
         * @param scatterRequest
         * @return randomStr
         */
    public CommonResponse scatter(ScatterRequest scatterRequest, int limitMin) {

        Assert.notNull(scatterRequest.getRoomId(),CoinScatterExceptionType.NO_ARG_ROOM_ID.getErrorMessage());
        Assert.notNull(scatterRequest.getHeadCount(),CoinScatterExceptionType.NO_ARG_HEAD_CNT.getErrorMessage());
        Assert.notNull(scatterRequest.getUserNo(),CoinScatterExceptionType.NO_ARG_USER_NO.getErrorMessage());
        Assert.notNull(scatterRequest.getTotalCoin(),CoinScatterExceptionType.NO_ARG_COINS.getErrorMessage());

        String token = generateToken(scatterRequest.getRoomId(), limitMin);

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

        scattererRepository.save(scatterer);

        return CommonResponse.success(TokenDTO.token(token));
    }


    private String generateToken(String roomId, int limitMin) {

        LocalDateTime limitAt = LocalDateTime.now().plusSeconds(limitMin);

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

            Boolean notFound = scattererRepository.findAllByRoomIdAndToken(roomId, randomStr)
                    .isEmpty();

            if (notFound) return randomStr;
        }
        // TODO: throw exception!!! - 토큰 오래 만듬! 타임아웃!!
        throw new CoinScatterException(CoinScatterExceptionType.TIMEOUT_GEN_TOKEN);

    }


    public CommonResponse receive(ReceiveRequest receiveRequest) {

        Assert.notNull(receiveRequest.getRoomId(),CoinScatterExceptionType.NO_ARG_ROOM_ID.getErrorMessage());
        Assert.notNull(receiveRequest.getUserNo(),CoinScatterExceptionType.NO_ARG_USER_NO.getErrorMessage());
        Assert.notNull(receiveRequest.getToken(),CoinScatterExceptionType.NO_ARG_TOKEN.getErrorMessage());

        LocalDateTime now = LocalDateTime.now();
        Long receiveUserNo = receiveRequest.getUserNo();

        // 조회 : 뿌리기 건( 대화방 + 토큰 )
        List<Scatterer> scatterers = scattererRepository
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
        // Validate : 모든인원이 받았는가?
        if (targetScatterer.getUserNo().equals(receiveUserNo)){
            // TODO: throw logical exception!!! - 뿌린사람이 또 받아?
            throw new CoinScatterException(CoinScatterExceptionType.SCATTER_OWNER);
        } else if(targetScatterer.getHeadCount().equals(targetScatterer.getReceiverCount())){
            // TODO: throw logical exception!!! - 모든 사람이 다 받았다!!
            throw new CoinScatterException(CoinScatterExceptionType.NO_COIN_LEFT);
        }

        List<Receiver> receivers = receiverRepository
                .findAllByScattererIdAndUserNo(targetScatterer.getId(),receiveRequest.getUserNo());

        // Validate : 받은사람이 또 받았는가?
        if (receivers.stream().anyMatch(r-> r.getUserNo().equals(receiveUserNo))){
            // TODO: throw logical exception!!! - 한번 받은사람이 또 받을 수 없습니다.
            throw new CoinScatterException(CoinScatterExceptionType.NOT_RECEIVE_AGAIN);
        }

        // 몇번째 차례인가? 마지막 차례이면 보너스 코인을 가져온다.
        Integer thisTurn = receivers.size() + 1;
        Long bonusCoin = 0L;
        if(thisTurn.equals(targetScatterer.getHeadCount())){
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

        receiverRepository.save(newReceiver);

        // 받기 처리 (scatterer) : update
        // scatterer.receiverCount = [update]
        // scatterer.remainCoin = [update] totalCoin - ( eachCoin * thisTurn[receiverCount] )
        targetScatterer.setReceiverCount(thisTurn);
        targetScatterer.setRemainCoin(targetScatterer.getRemainCoin() - (targetScatterer.getEachCoin() + bonusCoin));

        scattererRepository.save(targetScatterer);

        return CommonResponse.success(CoinDTO.coin(receivedCoin));
    }



    public CommonResponse display(ReceiveRequest receiveRequest) {

        // 조회 : 뿌리기 건( 대화방 + 토큰 )
        Scatterer scatterer = scattererRepository
                .findAllByRoomIdAndToken(receiveRequest.getRoomId(), receiveRequest.getToken())
                .stream().findFirst().orElseThrow(()->{
                    // TODO: throw logical exception!!! - 찾았는데 없음.
                    throw new CoinScatterException(CoinScatterExceptionType.NOT_FOUND_TOKEN);
                });

        List<Receiver> receivers = receiverRepository
                .findAllByScattererId(scatterer.getId());

        return CommonResponse.success(new DisplayDTO(scatterer, receivers));
    }

}
