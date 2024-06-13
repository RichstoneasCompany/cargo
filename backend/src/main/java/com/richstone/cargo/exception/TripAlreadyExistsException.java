package com.richstone.cargo.exception;

public class TripAlreadyExistsException extends RuntimeException {
    public TripAlreadyExistsException() {
    }

    public TripAlreadyExistsException(String message) {
        super(message);
    }
}
