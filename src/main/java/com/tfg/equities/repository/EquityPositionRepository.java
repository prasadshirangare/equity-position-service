package com.tfg.equities.repository;

import com.tfg.equities.model.entity.EquityPositionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EquityPositionRepository extends JpaRepository<EquityPositionEntity, Long> {

    List<EquityPositionEntity> findAllByTradeIdEqualsAndSecurityCodeEquals(Long tradeId, String securityCode);

}
