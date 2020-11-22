package com.ageusgji.toy.kakaoPay.coinScatter.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class ReceivererDTO {

    private Long userId;
    private Long coin;

}
