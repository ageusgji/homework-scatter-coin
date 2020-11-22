package com.ageusgji.toy.kakaoPay.coinScatter.domain;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.sun.istack.Nullable;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Scatterer extends CommonField {

    @Column(nullable = false)
    private Long userNo;

    @Column(nullable = false)
    private String roomId;

    @Column(nullable = false, length = 3)
    private String token;

    @Column(nullable = false)
    private Integer headCount;

    @Column
    private Integer receiverCount;

    @Column(nullable = false)
    private Long totalCoin;

    @Column
    private Long eachCoin;

    @Column
    private Long bonusCoin;

    @Column
    private Long remainCoin;

    @Column(nullable = false)
    private LocalDateTime expireDateTime;

    @Builder
    public Scatterer(Long userNo, String roomId, String token, Integer headCount, Integer receiverCount, Long totalCoin, Long eachCoin, Long bonusCoin, Long remainCoin, LocalDateTime expireDateTime) {
        this.userNo = userNo;
        this.roomId = roomId;
        this.token = token;
        this.headCount = headCount;
        this.receiverCount = receiverCount;
        this.totalCoin = totalCoin;
        this.eachCoin = eachCoin;
        this.bonusCoin = bonusCoin;
        this.remainCoin = remainCoin;
        this.expireDateTime = expireDateTime;
    }

}
