package com.ageusgji.toy.kakaoPay.coinScatter.web;

import com.ageusgji.toy.kakaoPay.coinScatter.util.exception.BaseExceptionHandler;
import com.ageusgji.toy.kakaoPay.coinScatter.util.exception.BaseExceptionType;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
public class CommonResponse {

    private Boolean result;
    private Integer code;
    private Integer status;
    private String msg;


    private Object data;

    public static CommonResponse success(Object data){
        return new CommonResponse(true, null, 200, "", data);
    }

    public static CommonResponse fail(Integer errCode, String errMsg){
        return new CommonResponse(false, errCode, 200, "요청한 작업이 실패했습니다.", null);
    }

    public static CommonResponse error(BaseExceptionType exception){
        return new CommonResponse(false,exception.getErrorCode(), exception.getHttpStatus(), exception.getErrorMessage(), null);
    }

}
