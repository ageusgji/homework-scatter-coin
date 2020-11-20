package com.ageusgji.toy.kakaoPay.coinScatter.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Scanner;

@Getter
@Setter
@Entity
public class Receiver extends CommonField {

    @Column(nullable = false)
    private Long scattererId;

    @Column
    private Long userNo;

    @Column
    private Long coin;

    @Column
    private Long bonusCoin;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JsonIgnore
//    @JoinColumn(name = "MENU_MNGT_SEQ", nullable = false)
//    private Scatter scatter;

/**
 * 	- id <PK>
 * 	- userId 			## 받은사람 id
 * 	- coin 				## 받은 금액
 * 	- rank
 * 	- rcvDateTime :=> regDateTime 으로 대체
 */


}
