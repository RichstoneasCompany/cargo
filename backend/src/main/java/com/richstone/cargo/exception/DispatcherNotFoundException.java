package com.richstone.cargo.exception;

public class DispatcherNotFoundException extends RuntimeException{
    public DispatcherNotFoundException() {

    }
    public DispatcherNotFoundException(String message, Throwable err) {
        super(message, err);
    }
}

