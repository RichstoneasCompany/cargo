package com.richstone.cargo.exception;

public class TruckNotFoundException extends RuntimeException{
    public TruckNotFoundException() {
    }
    public TruckNotFoundException(String message) {
        super(message);
    }
}
