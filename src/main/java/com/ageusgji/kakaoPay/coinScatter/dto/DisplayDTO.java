package com.ageusgji.toy.kakaoPay.coinScatter.dto;

import com.ageusgji.toy.kakaoPay.coinScatter.domain.Receiver;
import com.ageusgji.toy.kakaoPay.coinScatter.domain.Scatterer;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class DisplayDTO {

    private LocalDateTime scatterAt;
    private Long scatterCoin;
    private Long receivedCoin;

    private List<ReceivererDTO> receiverList;

    public DisplayDTO(Scatterer scatterer, List<Receiver> receiverList) {
        this.scatterAt = scatterer.getCreateAt();
        this.scatterCoin = scatterer.getTotalCoin();
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
