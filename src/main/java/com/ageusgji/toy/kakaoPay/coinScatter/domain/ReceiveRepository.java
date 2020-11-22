package com.ageusgji.toy.kakaoPay.coinScatter.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface ReceiveRepository extends JpaRepository<Receiver, Long> {
    List<Receiver> findAllByScattererId(Long scattererId);
//    List<Receiver> findAllByScattererIdAndUserNo(Long scattererId, Long userNo);
//    List<Receiver> findAllByScattererIdsAndUserNo(Set<Long> scattererIds, Long userNo);


}
