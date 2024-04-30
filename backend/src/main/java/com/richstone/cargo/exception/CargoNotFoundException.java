package com.richstone.cargo.exception;

public class CargoNotFoundException extends RuntimeException{
    public CargoNotFoundException() {
    }
    public CargoNotFoundException(String message) {
        super(message);
    }
}
