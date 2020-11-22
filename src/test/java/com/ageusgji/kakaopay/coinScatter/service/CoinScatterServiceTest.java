package com.ageusgji.kakaopay.coinScatter.service;

import com.ageusgji.kakaopay.coinScatter.dto.CoinDTO;
import com.ageusgji.kakaopay.coinScatter.dto.TokenDTO;
import com.ageusgji.kakaopay.coinScatter.util.enums.CoinScatterExceptionType;
import com.ageusgji.kakaopay.coinScatter.util.exception.CoinScatterException;
import com.ageusgji.kakaopay.coinScatter.web.CommonResponse;
import com.ageusgji.kakaopay.coinScatter.web.ReceiveRequest;
import com.ageusgji.kakaopay.coinScatter.web.ScatterRequest;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
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

    @Test
    public void scatterUserRequest_noRoomId(){
        ScatterRequest scatterUserRequest = ScatterRequest.builder()
                .userNo(100L)
                .roomId(null)
                .headCount(5)
                .totalCoin(7007L)
                .build();
        CommonResponse<TokenDTO> commonResponse;
        try {
            commonResponse = coinScatterService.scatter(scatterUserRequest);
        } catch (CoinScatterException e) {
            log.info(e.getMessage());
            assertEquals(e.getExceptionType(), CoinScatterExceptionType.NO_ARG_ROOM_ID);
        }
    }


    @Test
    public void scatterUserRequest_noUserNo(){
        ScatterRequest scatterUserRequest = ScatterRequest.builder()
                .userNo(null)
                .roomId("ARoom")
                .headCount(5)
                .totalCoin(7007L)
                .build();
        CommonResponse<TokenDTO> commonResponse;
        try {
            commonResponse = coinScatterService.scatter(scatterUserRequest);
        } catch (CoinScatterException e) {
            log.info(e.getMessage());
            assertEquals(e.getExceptionType(), CoinScatterExceptionType.NO_ARG_USER_NO);
        }
    }


    @Test
    public void scatterUserRequest_noHeadCount(){
        ScatterRequest scatterUserRequest = ScatterRequest.builder()
                .userNo(100L)
                .roomId("ARoom")
                .headCount(null)
                .totalCoin(7007L)
                .build();
        CommonResponse<TokenDTO> commonResponse;
        try {
            commonResponse = coinScatterService.scatter(scatterUserRequest);
        } catch (CoinScatterException e) {
            log.info(e.getMessage());
            assertEquals(e.getExceptionType(), CoinScatterExceptionType.NO_ARG_HEAD_CNT);
        }
    }


    @Test
    public void scatterUserRequest_noCoins(){
        ScatterRequest scatterUserRequest = ScatterRequest.builder()
                .userNo(100L)
                .roomId("ARoom")
                .headCount(5)
                .totalCoin(null)
                .build();
        CommonResponse<TokenDTO> commonResponse;
        try {
            commonResponse = coinScatterService.scatter(scatterUserRequest);
        } catch (CoinScatterException e) {
            log.info(e.getMessage());
            assertEquals(e.getExceptionType(), CoinScatterExceptionType.NO_ARG_COINS);
        }
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
            coinScatterService.scatter(scatterUserRequest, -1);
        } catch (CoinScatterException e) {
            final int errorCode = e.getExceptionType().getErrorCode();
            log.info(e.getMessage());
            Assertions.assertEquals(e.getExceptionType(), CoinScatterExceptionType.TIMEOUT_GEN_TOKEN);
        }

    }




    @Test
    public void receiveTest01() {
        ScatterRequest scatterUserRequest = ScatterRequest.builder()
                .userNo(100L)
                .roomId("BRoom")
                .headCount(3)
                .totalCoin(4030L)
                .build();

        CommonResponse<TokenDTO> commonResponse = coinScatterService.scatter(scatterUserRequest);
        String token = commonResponse.getData().getToken();

        ReceiveRequest receiveRequest = ReceiveRequest.builder()
                .userNo(1001L)
                .roomId("BRoom")
                .token(token)
                .build();


        CommonResponse<CoinDTO> coinDTOCommonResponse = coinScatterService.receive(receiveRequest);


        assertNotNull(coinDTOCommonResponse);
        assertNotNull(coinDTOCommonResponse.getData());
        assertTrue(coinDTOCommonResponse.getData().getCoin() > 0L);
    }


}