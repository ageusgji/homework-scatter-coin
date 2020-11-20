package com.ageusgji.toy.kakaoPay.coinScatter.web;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.util.Assert;

@Getter
@Setter
public class ScatterRequest {
    private Long userNo;
    private String roomId;
    private Integer headCount;
    private Long totalCoin;

    @Builder
    public ScatterRequest(Long userNo, String roomId, Integer headCount, Long totalCoin) {
//        Assert.isNull(userNo, "userNo must not be empty");

        this.userNo = userNo;
        this.roomId = roomId;
        this.headCount = headCount;
        this.totalCoin = totalCoin;
    }
}
