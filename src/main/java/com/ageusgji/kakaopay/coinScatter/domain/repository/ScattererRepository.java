package com.ageusgji.kakaopay.coinScatter.domain.repository;

import com.ageusgji.kakaopay.coinScatter.domain.entitiy.Scatterer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScattererRepository extends JpaRepository<Scatterer, Long> {
    List<Scatterer> findAllByRoomIdAndToken(String roomId, String token);


}
