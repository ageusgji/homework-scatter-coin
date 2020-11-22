package com.ageusgji.kakaopay.coinScatter.service;

import com.ageusgji.kakaopay.coinScatter.dto.TokenDTO;
import com.ageusgji.kakaopay.coinScatter.util.exception.CoinScatterException;
import com.ageusgji.kakaopay.coinScatter.web.CommonResponse;
import com.ageusgji.kakaopay.coinScatter.web.ScatterRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CoinScatterServiceTest {

    @Autowired
    CoinScatterService coinScatterService;

    // 뿌리기 기본
    @Test
    public void scatterTest01(){
        ScatterRequest scatterUserRequest = ScatterRequest.builder()
                .userNo(100L)
                .roomId("ARoom")
                .headCount(5)
                .totalCoin(7007L)
                .build();

        CommonResponse<TokenDTO> commonResponse = coinScatterService.scatter(scatterUserRequest);

        assertNotNull(commonResponse);
        assertNotNull(commonResponse.getData());
        assertTrue(commonResponse.getData().getToken().length() == 3);


    }

    // 뿌리기 입력 오류들
   @Test
    public void scatterParameters(){
        ScatterRequest scatterUserRequest = ScatterRequest.builder()
                .userNo(100L)
                .roomId("ARoom")
                .headCount(5)
                .totalCoin(7007L)
                .build();

        CommonResponse<TokenDTO> commonResponse = coinScatterService.scatter(scatterUserRequest);

        assertNotNull(commonResponse);
        assertNotNull(commonResponse.getData());
        assertTrue(commonResponse.getData().getToken().length() == 3);


    }

    // 뿌리기 예외 체크
    @Test
    public void scatter_토큰생성이_너무오래걸린다(){
        ScatterRequest scatterUserRequest = ScatterRequest.builder()
                .userNo(100L)
                .roomId("ARoom")
                .headCount(5)
                .totalCoin(7007L)
                .build();

        try {
            coinScatterService.scatter(scatterUserRequest);
        } catch (CoinScatterException e) {
            final int errorCode = e.getExceptionType().getErrorCode() ;
            Assertions.assertEquals(1001, errorCode);
        }

    }

}