package com.richstone.cargo.exception;

public class TripNotFoundException extends RuntimeException {
    public TripNotFoundException() {
    }

    public TripNotFoundException(String message, Throwable err) {
        super(message, err);
    }

    public TripNotFoundException(String message) {
        super(message);
    }
}
