package com.ageusgji.toy.kakaoPay.coinScatter.util.exception;

public interface BaseExceptionType {
    int getErrorCode();
    int getHttpStatus();
    String getErrorMessage();
}
