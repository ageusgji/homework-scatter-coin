package com.ageusgji.toy.kakaoPay.coinScatter.util.exception;

import com.ageusgji.toy.kakaoPay.coinScatter.web.CommonResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class BaseExceptionHandler {

    @ResponseBody
    @ExceptionHandler(CoinScatterException.class)
    public ResponseEntity<CommonResponse> commonException(CoinScatterException exception){
        return new ResponseEntity<>(CommonResponse.error(exception.getExceptionType()), HttpStatus.OK);
    }


}