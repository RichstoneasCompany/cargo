package com.richstone.cargo.exception;

public class DispatcherAlreadyExistException extends RuntimeException {
    public DispatcherAlreadyExistException() {
    }
    public DispatcherAlreadyExistException(String message) {
        super(message);
    }
    public DispatcherAlreadyExistException(String message, Throwable err) {
        super(message,err);
    }
}
