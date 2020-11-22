package com.ageusgji.toy.kakaoPay.coinScatter.web;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
public class CoinScatterResponse {
    private String result;
    private Object data;
    private String desc;

    private String token;

    private Long bonusCoin;
    private Integer turn;
    private Long receivedCoin;


    public CoinScatterResponse(String result, String desc, Long bonusCoin, Integer turn, Long receivedCoin) {
        this.result = result;
        this.desc = desc;
        this.bonusCoin = bonusCoin;
        this.turn = turn;
        this.receivedCoin = receivedCoin;
    }

}
