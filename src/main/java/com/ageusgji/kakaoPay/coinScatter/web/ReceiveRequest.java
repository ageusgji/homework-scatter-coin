package com.ageusgji.toy.kakaoPay.coinScatter.web;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReceiveRequest {
    private Long userNo;
    private String roomId;
    private String token;

    @Builder
    public ReceiveRequest(Long userNo, String roomId, String token) {
        this.userNo = userNo;
        this.roomId = roomId;
        this.token = token;
    }

}
