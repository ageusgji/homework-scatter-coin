package com.ageusgji.kakaopay.coinScatter.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
public class CoinDTO {

    private Long coin;

    public static CoinDTO coin(Long coin){
        CoinDTO s = new CoinDTO();
        s.setCoin(coin);
        return s;
    }

}
