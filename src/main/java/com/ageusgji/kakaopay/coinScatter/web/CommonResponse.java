package com.ageusgji.kakaopay.coinScatter.web;

import com.ageusgji.kakaopay.coinScatter.util.exception.BaseExceptionType;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
public class CommonResponse<T> {

    private Boolean result;
    private Integer code;
    private Integer status;
    private String msg;

    private T data;

    public static <T> CommonResponse success(T data) {
        return new CommonResponse(true, null, 200, "", data);
    }

    public static CommonResponse fail(BaseExceptionType e) {
        return new CommonResponse(false,  e.getErrorCode(), HttpStatus.EXPECTATION_FAILED.value(), e.getErrorMessage(), null);
    }

    public static CommonResponse error(BaseExceptionType e) {
        return new CommonResponse(false, e.getErrorCode(), e.getHttpStatus(), e.getErrorMessage(), null);
    }

}
