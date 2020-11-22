package com.ageusgji.toy.kakaoPay.coinScatter.domain;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;

@Getter
@Setter
@NoArgsConstructor
@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Receiver extends CommonField {

    @Column(nullable = false)
    private Long scattererId;

    @Column
    private Long userNo;

    @Column
    private Long coin;

    @Column
    private Integer turn;

    @Builder
    public Receiver(Long scattererId, Long userNo, Long coin, Integer turn) {
        this.scattererId = scattererId;
        this.userNo = userNo;
        this.coin = coin;
        this.turn = turn;
    }



}
