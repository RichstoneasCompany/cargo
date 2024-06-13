package com.richstone.cargo.exception;

public class TripNotInProgressException extends RuntimeException {
    public TripNotInProgressException() {
    }

    public TripNotInProgressException(String message) {
        super(message);
    }
}
