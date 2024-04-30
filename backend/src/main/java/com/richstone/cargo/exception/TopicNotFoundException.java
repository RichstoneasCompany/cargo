package com.richstone.cargo.exception;

public class TopicNotFoundException extends RuntimeException {
    public TopicNotFoundException() {
    }
    public TopicNotFoundException(String message) {
        super(message);
    }

    public TopicNotFoundException(String message, Throwable err) {
        super(message, err);
    }
}
