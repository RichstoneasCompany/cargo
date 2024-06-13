package com.richstone.cargo.exception;

public class TripInProgressException extends RuntimeException {
    public TripInProgressException() {
    }

    public TripInProgressException(String message) {
        super(message);
    }
}

