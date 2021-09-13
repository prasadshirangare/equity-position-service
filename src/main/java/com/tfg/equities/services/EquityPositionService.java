package com.tfg.equities.services;

import com.tfg.equities.exception.PositionException;
import com.tfg.equities.model.dto.EquityPositionDTO;
import com.tfg.equities.model.entity.EquityPositionEntity;
import com.tfg.equities.repository.EquityPositionRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

import static com.tfg.equities.model.entity.ACTION.CANCEL;
import static com.tfg.equities.model.entity.ACTION.UPDATE;
import static com.tfg.equities.model.entity.POSITION.BUY;

@Service
public class EquityPositionService {

    private final EquityPositionRepository repository;

    public EquityPositionService(EquityPositionRepository repository) {
        this.repository = repository;
    }

    public List<EquityPositionDTO> getALLPositions() {
        return repository.findAll()
                .stream()
                .map(position -> new EquityPositionDTO(position.getTransactionId(), position.getTradeId(),
                        position.getVersion(), position.getSecurityCode(), position.getQuantity(),
                        position.getAction(), position.getPosition()))
                .collect(Collectors.toList());
    }

    public List<EquityPositionDTO> getALLPositionsByTradeId(Long tradeId) {
        List<EquityPositionDTO> allPositions = getALLPositions();
        return allPositions.stream()
                .filter(position -> position.getTradeId().equals(tradeId))
                .collect(Collectors.toList());
    }

    @Transactional
    public EquityPositionDTO insertPosition(EquityPositionDTO position) {
        // Create a position entity from DTO
        EquityPositionEntity newPosition = new EquityPositionEntity(position.getTradeId(), 1L, position.getSecurityCode()
                , position.getQuantity(), position.getAction(), position.getPosition());
        // Save to database
        newPosition = repository.save(newPosition);
        // Map saved entity to DTO for sending response to User
        EquityPositionDTO positionDTO = new EquityPositionDTO(newPosition.getTransactionId(), newPosition.getTradeId(),
                newPosition.getVersion(), newPosition.getSecurityCode(), newPosition.getQuantity(), newPosition.getAction(),
                newPosition.getPosition());
        return positionDTO;
    }

    @Transactional
    public EquityPositionDTO updatePosition(EquityPositionDTO position) throws PositionException {
        List<EquityPositionEntity> positionEntityList = repository.findAllByTradeIdEqualsAndSecurityCodeEquals(position.getTradeId(),
                position.getSecurityCode());
        if (positionEntityList == null)
            throw new PositionException(String.format("No position found for trade-id: %s", position.getTradeId()));

        // not sure about incrementing version for update transaction
        long version = getMaxVersion(positionEntityList);
        long quantity = positionEntityList.stream()
                .map(EquityPositionEntity::getQuantity)
                .mapToLong(v -> v)
                .max()
                .orElseThrow();
        long newQuantity = position.getPosition().equals(BUY) ? quantity + position.getQuantity() : quantity - position.getQuantity();
        EquityPositionEntity updatedPosition = new EquityPositionEntity(position.getTradeId(), version + 1, position.getSecurityCode()
                , newQuantity, UPDATE, position.getPosition());

        EquityPositionEntity entity = repository.save(updatedPosition);
        return new EquityPositionDTO(entity.getTransactionId(), entity.getTradeId(),
                entity.getVersion(), entity.getSecurityCode(), entity.getQuantity(), entity.getAction(),
                entity.getPosition());
    }

    @Transactional
    public EquityPositionDTO cancelPosition(EquityPositionDTO position) throws PositionException {
        List<EquityPositionEntity> positionEntityList = repository.findAllByTradeIdEqualsAndSecurityCodeEquals(position.getTradeId(),
                position.getSecurityCode());
        if (positionEntityList == null)
            throw new PositionException(String.format("No position found for trade-id: %s", position.getTradeId()));

        EquityPositionEntity existingTrade = positionEntityList.stream().findFirst().get();
        long version = getMaxVersion(positionEntityList);

        EquityPositionEntity cancelledPosition = new EquityPositionEntity(position.getTradeId(), version + 1, existingTrade.getSecurityCode()
                , position.getQuantity(), CANCEL, position.getPosition());

        EquityPositionEntity entity = repository.save(cancelledPosition);
        return new EquityPositionDTO(entity.getTransactionId(), entity.getTradeId(),
                entity.getVersion(), entity.getSecurityCode(), entity.getQuantity(), entity.getAction(),
                entity.getPosition());

    }

    private long getMaxVersion(List<EquityPositionEntity> positionEntityList) {
        return positionEntityList.stream()
                .map(EquityPositionEntity::getVersion)
                .mapToLong(v -> v)
                .max()
                .orElseThrow();
    }

}
