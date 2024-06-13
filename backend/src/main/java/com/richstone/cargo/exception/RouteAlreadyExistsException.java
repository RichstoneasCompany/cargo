package com.richstone.cargo.exception;

public class RouteAlreadyExistsException extends RuntimeException{
    public RouteAlreadyExistsException() {
    }
    public RouteAlreadyExistsException(String message) {
        super(message);
    }
}
