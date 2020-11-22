package com.ageusgji.kakaopay.coinScatter.service;

import com.ageusgji.kakaopay.coinScatter.dto.TokenDTO;
import com.ageusgji.kakaopay.coinScatter.util.enums.CoinScatterExceptionType;
import com.ageusgji.kakaopay.coinScatter.util.exception.CoinScatterException;
import com.ageusgji.kakaopay.coinScatter.web.CommonResponse;
import com.ageusgji.kakaopay.coinScatter.web.ScatterRequest;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
class CoinScatterServiceIntegrTest {

    @Autowired
    CoinScatterService coinScatterService;

    @Test
    public void scatter_받은사람이_또_받았다(){
        ScatterRequest scatterUserRequest = ScatterRequest.builder()
                .userNo(100L)
                .roomId("ARoom")
                .headCount(5)
                .totalCoin(7007L)
                .build();

        try {
            coinScatterService.scatter(scatterUserRequest, -1);
        } catch (CoinScatterException e) {
            final int errorCode = e.getExceptionType().getErrorCode();
            log.info(e.getMessage());
            Assertions.assertEquals(e.getExceptionType(), CoinScatterExceptionType.TIMEOUT_GEN_TOKEN);
        }

    }



}