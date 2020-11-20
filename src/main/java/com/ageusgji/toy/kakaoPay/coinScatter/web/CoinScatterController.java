package com.ageusgji.toy.kakaoPay.coinScatter.web;


import com.ageusgji.toy.kakaoPay.coinScatter.service.CoinScatterService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@Api(value = "CoinScatterController V2")
@RestController
@ControllerAdvice
public class CoinScatterController {
    @Autowired
    private CoinScatterService coinScatterService;

    @GetMapping(value = "/scatter")
    public ResponseEntity<String> scatter(@RequestHeader("X-USER-ID") Long userNo,
                                          @RequestHeader("X-ROOM-ID") String roomId,
                                          @RequestParam(name="headCnt") Integer headCount,
                                          @RequestParam(name="coins") Long totalCoin) {

        ScatterRequest scatterRequest = ScatterRequest.builder()
                .userNo(userNo)
                .roomId(roomId)
                .headCount(headCount)
                .totalCoin(totalCoin)
                .build();

        String token = coinScatterService.scatter(scatterRequest);

        return new ResponseEntity<>("\n token = "+ token, HttpStatus.OK);

    }



    @RequestMapping(value = {"/testConn","/"}, method = RequestMethod.GET)
    public ResponseEntity<String> testConnect() {

        try {


            return new ResponseEntity<>("Success!!", HttpStatus.OK);

        } catch (Exception e) {

            return new ResponseEntity<>("Error!??", HttpStatus.OK);

        }

    }
}
