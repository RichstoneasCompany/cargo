package com.richstone.cargo.exception;

public class IncomeNotFoundException extends RuntimeException{
    public IncomeNotFoundException() {
    }
    public IncomeNotFoundException(String message, Throwable err) {
        super(message,err);
    }
}
