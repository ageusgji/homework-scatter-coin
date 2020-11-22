package com.ageusgji.toy.kakaoPay.coinScatter.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SingleDTO {

    private String token;
    private Long coin;

    public static SingleDTO token(String token){
        SingleDTO s = new SingleDTO();
        s.setToken(token);
        return s;
    }

    public static SingleDTO coin(Long coin){
        SingleDTO s = new SingleDTO();
        s.setCoin(coin);
        return s;
    }

}
