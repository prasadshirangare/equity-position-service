package com.tfg.equities.model.entity;

public enum POSITION {
    BUY("Buy"), SELL("Sell");

    POSITION(String call) {
        this.call = call;
    }

    private final String call;

    public String getCall() {
        return call;
    }
}
