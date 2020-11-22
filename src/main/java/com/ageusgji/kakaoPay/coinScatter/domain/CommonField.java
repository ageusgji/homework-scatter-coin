package com.ageusgji.toy.kakaoPay.coinScatter.domain;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.security.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;

@Getter
@MappedSuperclass
abstract class CommonField {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreationTimestamp
    private LocalDateTime createAt;
    @UpdateTimestamp
    private LocalDateTime updateAt;

}
