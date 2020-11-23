package com.ageusgji.kakaopay.coinScatter.dto;

import com.ageusgji.kakaopay.coinScatter.domain.entitiy.Receiver;
import com.ageusgji.kakaopay.coinScatter.domain.entitiy.Scatterer;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class DisplayDTO {

    private LocalDateTime scatterAt;
    private Long totalCoin;
    private Long receivedCoin;

    private List<ReceivererDTO> receiverList;

    public DisplayDTO(Scatterer scatterer, List<Receiver> receiverList) {
        this.scatterAt = scatterer.getCreateAt();
        this.totalCoin = scatterer.getTotalCoin();
        this.receivedCoin = scatterer.getTotalCoin() - scatterer.getRemainCoin();
        List<ReceivererDTO> receivers = receiverList.stream().map(r->{
            ReceivererDTO receivererDto = new ReceivererDTO();
            receivererDto.setCoin(r.getCoin());
            receivererDto.setUserId(r.getUserNo());
            return receivererDto;
        }).collect(Collectors.toList());

        this.receiverList = receivers;
    }
}
