package com.tfg.equities.services;

import com.tfg.equities.exception.PositionException;
import com.tfg.equities.model.dto.EquityPositionDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static com.tfg.equities.model.entity.ACTION.INSERT;
import static com.tfg.equities.model.entity.POSITION.BUY;
import static com.tfg.equities.model.entity.POSITION.SELL;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@ExtendWith(SpringExtension.class)
@Import(EquityPositionService.class)
class EquityPositionServiceTest {

    @Autowired
    private EquityPositionService unitToTest;

    @Test
    void getALLPositions() {
        List<EquityPositionDTO> allPositions = unitToTest.getALLPositions();
        assertEquals(6, allPositions.size());
    }

    @Test
    void getALLPositionsByTradeId() {
        List<EquityPositionDTO> allPositions = unitToTest.getALLPositionsByTradeId(1L);
        assertEquals(2, allPositions.size());
    }

    @Test
    @DirtiesContext
    void insertPosition() {
        EquityPositionDTO newPosition = new EquityPositionDTO(-1L, 50L, -1L, "IBM", 60L, INSERT, BUY);
        EquityPositionDTO position = unitToTest.insertPosition(newPosition);
        List<EquityPositionDTO> allPositions = unitToTest.getALLPositionsByTradeId(50L);
        assertEquals(1, allPositions.size());
    }

    @Test
    @DirtiesContext
    void updatePosition() throws PositionException {
        EquityPositionDTO newPosition = new EquityPositionDTO(-1L, 50L, -1L, "IBM", 60L, INSERT, BUY);
        EquityPositionDTO position = unitToTest.insertPosition(newPosition);
        position.setPosition(SELL);
        position.setQuantity(20L);

        EquityPositionDTO updatePosition = unitToTest.updatePosition(position);
        assertEquals(40L, updatePosition.getQuantity());
        assertEquals("IBM", updatePosition.getSecurityCode());
    }

    @Test
    @DirtiesContext
    void cancelPosition() throws Exception {
        EquityPositionDTO newPosition = new EquityPositionDTO(-1L, 50L, -1L, "IBM", 60L, INSERT, BUY);
        EquityPositionDTO position = unitToTest.insertPosition(newPosition);
        EquityPositionDTO cancelPosition = unitToTest.cancelPosition(position);
        assertEquals(2L, cancelPosition.getVersion());
        assertEquals("IBM", cancelPosition.getSecurityCode());
    }


}