package com.ageusgji.kakaopay.coinScatter.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
public class TokenDTO {

    private String token;

    public static TokenDTO token(String token){
        TokenDTO s = new TokenDTO();
        s.setToken(token);
        return s;
    }

}
