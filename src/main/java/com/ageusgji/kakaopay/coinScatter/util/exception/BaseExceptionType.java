package com.ageusgji.kakaopay.coinScatter.util.exception;

public interface BaseExceptionType {
    int getErrorCode();
    int getHttpStatus();
    String getErrorMessage();
}
