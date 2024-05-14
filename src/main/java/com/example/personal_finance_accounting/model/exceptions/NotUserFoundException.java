package com.example.personal_finance_accounting.model.exceptions;

public class NotUserFoundException extends RuntimeException{
    public NotUserFoundException(String message) {
        super(message);
    }
}
