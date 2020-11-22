package com.ageusgji.toy.kakaoPay.coinScatter.util.exception;

import lombok.Getter;

public class CoinScatterException extends RuntimeException {

        @Getter
        private BaseExceptionType exceptionType;

        public CoinScatterException(BaseExceptionType exceptionType){
            super(exceptionType.getErrorMessage());
            this.exceptionType = exceptionType;
        }

}
