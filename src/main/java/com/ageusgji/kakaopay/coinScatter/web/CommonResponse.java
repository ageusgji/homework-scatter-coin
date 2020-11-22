package com.ageusgji.kakaopay.coinScatter.web;

import com.ageusgji.kakaopay.coinScatter.util.exception.BaseExceptionType;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

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

    public static CommonResponse fail(Integer errCode, String errMsg) {
        return new CommonResponse(false, errCode, 200, "요청한 작업이 실패했습니다.", null);
    }

    public static CommonResponse error(BaseExceptionType exception) {
        return new CommonResponse(false, exception.getErrorCode(), exception.getHttpStatus(), exception.getErrorMessage(), null);
    }

}
