package com.ageusgji.kakaopay.coinScatter.util.exception;

import com.ageusgji.kakaopay.coinScatter.web.CommonResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class BaseExceptionHandler {

    @ResponseBody
    @ExceptionHandler(CoinScatterException.class)
    public ResponseEntity<CommonResponse> commonException(CoinScatterException e) {
        return new ResponseEntity<>(CommonResponse.error(e.getExceptionType()), HttpStatus.OK);
    }

    @ResponseBody
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<CommonResponse> illegalArgException(CoinScatterException e) {
        return new ResponseEntity<>(CommonResponse.fail(e.getExceptionType()), HttpStatus.OK);
    }


}