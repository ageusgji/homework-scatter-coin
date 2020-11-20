package com.ageusgji.toy.kakaoPay.coinScatter.service;

import com.ageusgji.toy.kakaoPay.coinScatter.domain.ScatterRepository;
import com.ageusgji.toy.kakaoPay.coinScatter.domain.Scatterer;
import com.ageusgji.toy.kakaoPay.coinScatter.web.ScatterRequest;
import jdk.jshell.execution.LoaderDelegate;
import org.springframework.beans.BeanUtils;
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

    /**
     * https://www.baeldung.com/java-random-string
     * @param scatterRequest
     * @return randomStr
     */
    public String scatter(ScatterRequest scatterRequest)  {

        String token = generateToken(scatterRequest.getRoomId());



        Long eachCoin =  scatterRequest.getTotalCoin() / scatterRequest.getHeadCount();
        // receiver.bonusCoin = totalCoin - ( eachCoin * receiverCount )
        // scatterer.receiverCount = [update]
        // scatterer.remainCoin = [update] totalCoin - ( eachCoin * receiverCount )


        LocalDateTime currentDateTime = LocalDateTime.now();
        currentDateTime.plusMinutes(10);


        Scatterer scatterer = Scatterer.builder()
                .userNo(scatterRequest.getUserNo())
                .roomId(scatterRequest.getRoomId())
                .headCount(scatterRequest.getHeadCount())
                .totalCoin(scatterRequest.getTotalCoin())
                .eachCoin(eachCoin)
                .expireDateTime(currentDateTime)
                .token(token)
                .remainCoin(scatterRequest.getTotalCoin())
                .receiverCount(0)
                .build();

        scatterRepository.save(scatterer);


        return token;

    }



    private String generateToken(String roomId)  {

        LocalDateTime startAt = LocalDateTime.now();
        LocalDateTime limitAt = LocalDateTime.now().plusSeconds(60);

        while (startAt.isBefore(limitAt)) {

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
                    .stream().filter(a -> a.getExpireDateTime().isAfter(startAt))
                    .collect(Collectors.toList())
                    .isEmpty();

            if (notFound) return randomStr;
        }
        throw new RuntimeException();
        // TODO: throw exception!!!

    }


}
