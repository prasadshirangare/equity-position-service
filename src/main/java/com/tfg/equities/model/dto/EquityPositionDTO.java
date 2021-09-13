package com.tfg.equities.model.dto;

import com.tfg.equities.model.entity.ACTION;
import com.tfg.equities.model.entity.POSITION;
import lombok.Data;

@Data
public class EquityPositionDTO {

    private Long transactionId;

    private Long tradeId;

    private Long version;

    private String securityCode;

    private Long quantity;

    private ACTION action;

    private POSITION position;

    public EquityPositionDTO() {
    }

    public EquityPositionDTO(Long transactionId, Long tradeId, Long version, String securityCode, Long quantity, ACTION action, POSITION position) {
        this.transactionId = transactionId;
        this.tradeId = tradeId;
        this.version = version;
        this.securityCode = securityCode;
        this.quantity = quantity;
        this.action = action;
        this.position = position;
    }
}
