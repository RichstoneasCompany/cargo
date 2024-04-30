package com.richstone.cargo.exception;

public class ExpenseNotFoundException extends RuntimeException {
    public ExpenseNotFoundException() {
    }
    public ExpenseNotFoundException(String message, Throwable err) {
        super(message, err);
    }
}
