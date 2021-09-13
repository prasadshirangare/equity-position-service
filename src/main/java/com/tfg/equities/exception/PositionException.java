package com.tfg.equities.exception;

import java.io.Serializable;

public class PositionException extends Exception implements Serializable {

    public PositionException() {
        super();
    }

    public PositionException(String message) {
        super(message);
    }

}
