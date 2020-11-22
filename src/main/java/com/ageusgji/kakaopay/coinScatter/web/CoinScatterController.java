package com.ageusgji.kakaopay.coinScatter.web;


import com.ageusgji.kakaopay.coinScatter.service.CoinScatterService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@Api(value = "CoinScatterController V2")
@RestController
public class CoinScatterController {
    @Autowired
    private CoinScatterService coinScatterService;

    @GetMapping(value = "/scatter")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-USER-ID", paramType = "header", defaultValue = "111"),
            @ApiImplicitParam(name = "X-ROOM-ID", paramType = "header", defaultValue = "rrr"),
            @ApiImplicitParam(name = "headCount", paramType = "query", defaultValue = "3"),
            @ApiImplicitParam(name = "coins", paramType = "query", defaultValue = "1000")
    })
    public ResponseEntity<CommonResponse> scatter(@RequestHeader("X-USER-ID") Long userNo,
                                          @RequestHeader("X-ROOM-ID") String roomId,
                                          @RequestParam(name="headCount") Integer headCount,
                                          @RequestParam(name="coins") Long totalCoin) {

        ScatterRequest scatterRequest = ScatterRequest.builder()
                .userNo(userNo)
                .roomId(roomId)
                .headCount(headCount)
                .totalCoin(totalCoin)
                .build();

        CommonResponse response = coinScatterService.scatter(scatterRequest);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @GetMapping(value = "/receive")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-USER-ID", paramType = "header", defaultValue = "1111"),
            @ApiImplicitParam(name = "X-ROOM-ID", paramType = "header", defaultValue = "rrr")
    })
    public ResponseEntity<CommonResponse> receive(@RequestHeader("X-USER-ID") Long userNo,
                                          @RequestHeader("X-ROOM-ID") String roomId,
                                          @RequestParam(name="token") String token){

        ReceiveRequest receiveRequest = ReceiveRequest.builder()
                .userNo(userNo)
                .roomId(roomId)
                .token(token)
                .build();

        CommonResponse response = coinScatterService.receive(receiveRequest);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value = "/display")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-USER-ID", paramType = "header", defaultValue = "111"),
            @ApiImplicitParam(name = "X-ROOM-ID", paramType = "header", defaultValue = "rrr")
    })
    public ResponseEntity<CommonResponse> display(@RequestHeader("X-USER-ID") Long userNo,
                                                  @RequestHeader("X-ROOM-ID") String roomId,
                                                  @RequestParam(name="token") String token){

        ReceiveRequest receiveRequest = ReceiveRequest.builder()
                .userNo(userNo)
                .roomId(roomId)
                .token(token)
                .build();

        CommonResponse response = coinScatterService.display(receiveRequest);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
