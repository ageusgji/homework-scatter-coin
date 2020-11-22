package com.ageusgji.toy.kakaoPay.coinScatter.web;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ScatterRequest {
    private Long userNo;
    private String roomId;
    private Integer headCount;
    private Long totalCoin;

    @Builder
    public ScatterRequest(Long userNo, String roomId, Integer headCount, Long totalCoin) {
        this.userNo = userNo;
        this.roomId = roomId;
        this.headCount = headCount;
        this.totalCoin = totalCoin;
    }
}
