package com.ageusgji.kakaopay.coinScatter.domain.repository;

import com.ageusgji.kakaopay.coinScatter.domain.entitiy.Receiver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReceiverRepository extends JpaRepository<Receiver, Long> {
    List<Receiver> findAllByScattererIdAndUserNo(Long scattererId, Long userNo);
    List<Receiver> findAllByScattererId(Long scattererId);
//    List<Receiver> findAllByScattererIdsAndUserNo(Set<Long> scattererIds, Long userNo);


}
