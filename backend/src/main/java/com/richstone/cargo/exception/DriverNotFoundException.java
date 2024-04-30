package com.richstone.cargo.exception;

public class DriverNotFoundException extends RuntimeException {
    public DriverNotFoundException() {
    }
    public DriverNotFoundException(String message, Throwable err) {
        super(message, err);
    }
    public DriverNotFoundException(String message) {
        super(message);
    }
}
