package com.ageusgji.kakaopay.coinScatter.domain.repository;

import com.ageusgji.kakaopay.coinScatter.domain.entitiy.Receiver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
<<<<<<< HEAD:src/main/java/com/ageusgji/toy/kakaoPay/coinScatter/domain/ReceiveRepository.java
public interface ReceiveRepository extends JpaRepository<Receiver, Long> {
=======
public interface ReceiverRepository extends JpaRepository<Receiver, Long> {
    List<Receiver> findAllByScattererIdAndUserNo(Long scattererId, Long userNo);
>>>>>>> master:src/main/java/com/ageusgji/kakaopay/coinScatter/domain/repository/ReceiverRepository.java
    List<Receiver> findAllByScattererId(Long scattererId);
//    List<Receiver> findAllByScattererIdAndUserNo(Long scattererId, Long userNo);
//    List<Receiver> findAllByScattererIdsAndUserNo(Set<Long> scattererIds, Long userNo);


}
