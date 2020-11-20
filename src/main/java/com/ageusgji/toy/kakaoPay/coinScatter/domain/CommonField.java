package com.ageusgji.toy.kakaoPay.coinScatter.domain;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.security.Timestamp;
import java.util.Date;

@MappedSuperclass
abstract class CommonField {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @CreationTimestamp
    private Date createAt;
    @UpdateTimestamp
    private Date updateAt;

}
