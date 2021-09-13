package com.tfg.equities.model.entity;

public enum ACTION {

    INSERT("INSERT"), UPDATE("UPDATE"), CANCEL("CANCEL");
    private final String action;

    ACTION(String action) {
        this.action = action;
    }

    public String getAction() {
        return action;
    }
}
