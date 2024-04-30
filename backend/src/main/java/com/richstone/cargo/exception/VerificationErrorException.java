package com.richstone.cargo.exception;

public class VerificationErrorException extends RuntimeException{
    public VerificationErrorException(String message) {
        super(message);
    }
}
