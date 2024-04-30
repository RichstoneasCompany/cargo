package com.richstone.cargo.exception;

public class QuestionNotFoundException extends RuntimeException {
    public QuestionNotFoundException() {
    }

    public QuestionNotFoundException(String message, Throwable err) {
        super(message, err);
    }
}
