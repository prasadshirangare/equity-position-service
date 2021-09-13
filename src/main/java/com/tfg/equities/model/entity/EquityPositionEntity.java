package com.tfg.equities.model.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "EQUITY_POSITION")
public class EquityPositionEntity {

    /**
     * Unique transaction id, Primary key
     */
    @Id
    @GeneratedValue
    @Column(name = "TRANSACTION_ID")
    private Long transactionId;

    /**
     * Trade identifier
     */
    @Column(name = "TRADE_ID")
    private Long tradeId;

    /**
     * Trade version
     */
    @Column(name = "VERSION")
    private Long version;

    /**
     * Unique security identifier
     */
    @Column(name = "SECURITY_CODE")
    private String securityCode;

    /**
     * Position quantity
     */
    @Column(name = "QUANTITY")
    private Long quantity;

    /**
     * Action on equity position, INSERT/UPDATE/DELETE
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "ACTION")
    private ACTION action;

    /**
     * Equity position call, BUY/SELL
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "POSITION")
    private POSITION position;

    public EquityPositionEntity() {
        super();
    }

    public EquityPositionEntity(Long tradeId, Long version, String securityCode, Long quantity, ACTION action, POSITION position) {
        this.tradeId = tradeId;
        this.version = version;
        this.securityCode = securityCode;
        this.quantity = quantity;
        this.action = action;
        this.position = position;
    }

    public EquityPositionEntity(Long transactionId, Long tradeId, Long version, String securityCode, Long quantity, ACTION action, POSITION position) {
        this(tradeId, version, securityCode, quantity, action, position);
        this.transactionId = transactionId;
    }
}
