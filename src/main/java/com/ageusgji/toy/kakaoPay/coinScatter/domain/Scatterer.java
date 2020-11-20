package com.ageusgji.toy.kakaoPay.coinScatter.domain;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
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
    private Long remainCoin;

    @Column(nullable = false)
    private LocalDateTime expireDateTime;

    @Builder
    public Scatterer(Long userNo, String roomId, String token, Integer headCount, Integer receiverCount, Long totalCoin, Long eachCoin, Long remainCoin, LocalDateTime expireDateTime) {
        this.userNo = userNo;
        this.roomId = roomId;
        this.token = token;
        this.headCount = headCount;
        this.receiverCount = receiverCount;
        this.totalCoin = totalCoin;
        this.eachCoin = eachCoin;
        this.remainCoin = remainCoin;
        this.expireDateTime = expireDateTime;
    }

//    @OneToMany(mappedBy = "scatter", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    @JsonIgnore
//    private List<Receiver> receivers = new ArrayList<>(1);


/**
 * ## tokenBooks
 * 	- tokenBooksUID <PK>
 * 	- sctKey <FK>
 * 	- tknName			## 토큰이 한정적이라. Status를 확인해서 없는걸 줘야 한다. 토큰이 풀차면 잠시 기다려야함.
 * 	- tknStatus			## 토큰이 한정적이라.
 * 	- tknExpireDateTime
 * 	- sctRoomId
 *
 * 	## scatter
 * 	// - id <PK>
 * 	- userId : 뿌린사람 id
 * 	- sctRoomId
 * 	// - tknName
 * 	(del)- sctKey			## tknName + sctUserId + sctRoomId + (sctNumberOfPerson)
 * 	(필요?)- tokenBooksUID
 * 	- numberOfPerson			## 뿌릴인원
 * 	- totalCoin					## 뿌릴금액
 * 	- sctEachCoin				[분배로직이 들어가야함]
 * 	- sctRemainCoin				[받고 남은 분배 마지막 사람이 가져감]
 */


}
