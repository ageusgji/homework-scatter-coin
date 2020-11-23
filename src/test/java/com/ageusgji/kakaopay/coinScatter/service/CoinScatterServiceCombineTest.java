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

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
class CoinScatterServiceCombineTest {

    @Autowired
    CoinScatterService coinScatterService;

    @Test
    public void scatter_받은사람이_또_받았다(){
        ScatterRequest scatterUserRequest = ScatterRequest.builder()
                .userNo(500L)
                .roomId("RoRoom")
                .headCount(7)
                .totalCoin(234030L)
                .build();

        CommonResponse<TokenDTO> commonResponse = coinScatterService.scatter(scatterUserRequest);
        String token = commonResponse.getData().getToken();

        ReceiveRequest receiveRequest01 = ReceiveRequest.builder()
                .userNo(1001L)
                .roomId("RoRoom")
                .token(token)
                .build();

        coinScatterService.receive(receiveRequest01);

        ReceiveRequest receiveRequest02 = ReceiveRequest.builder()
                .userNo(1001L)
                .roomId("RoRoom")
                .token(token)
                .build();

        CommonResponse<CoinDTO> coinDTOCommonResponse;

        try {
            coinDTOCommonResponse = coinScatterService.receive(receiveRequest02);
        } catch (CoinScatterException e) {
            log.info(e.getMessage());
            Assertions.assertEquals(e.getExceptionType(), CoinScatterExceptionType.NOT_RECEIVE_AGAIN);
        }

    }

    @Test
    public void scatter_모든사람이_다받았다(){

        int headCount = 3;
        ScatterRequest scatterUserRequest = ScatterRequest.builder()
                .userNo(810L)
                .roomId("AllRoom")
                .headCount(headCount)
                .totalCoin(833749L)
                .build();

        CommonResponse<TokenDTO> commonResponse = coinScatterService.scatter(scatterUserRequest);
        String token = commonResponse.getData().getToken();

        for (int i = 0; i < headCount; i++) {
            ReceiveRequest receiveRequest = ReceiveRequest.builder()
                    .userNo(8001L+i)
                    .roomId("AllRoom")
                    .token(token)
                    .build();

            coinScatterService.receive(receiveRequest);
        }



        ReceiveRequest receiveRequest_tooLate = ReceiveRequest.builder()
                .userNo(8001L+headCount)
                .roomId("AllRoom")
                .token(token)
                .build();

        CommonResponse<CoinDTO> coinDTOCommonResponse;

        try {
            coinDTOCommonResponse =  coinScatterService.receive(receiveRequest_tooLate);
        } catch (CoinScatterException e) {
            log.info(e.getMessage());
            //모든사람이 다 받아서, 남은금액이 없다.
            Assertions.assertEquals(e.getExceptionType(), CoinScatterExceptionType.NO_COIN_LEFT);
        }

    }

    @Test
    public void receive_뿌린사람이_받을수업습니다(){
        int headCount = 3;
        ScatterRequest scatterUserRequest = ScatterRequest.builder()
                .userNo(910L)
                .roomId("SameRoom")
                .headCount(headCount)
                .totalCoin(833749L)
                .build();

        CommonResponse<TokenDTO> commonResponse = coinScatterService.scatter(scatterUserRequest);
        String token = commonResponse.getData().getToken();


        ReceiveRequest receiveRequest = ReceiveRequest.builder()
                .userNo(910L)
                .roomId("SameRoom")
                .token(token)
                .build();

        CommonResponse<CoinDTO> coinDTOCommonResponse;
        try {
            coinDTOCommonResponse =  coinScatterService.receive(receiveRequest);
        } catch (CoinScatterException e) {
            log.info(e.getMessage());
            Assertions.assertEquals(e.getExceptionType(), CoinScatterExceptionType.NOT_SCATTER_OWNER);
        }

    }

}