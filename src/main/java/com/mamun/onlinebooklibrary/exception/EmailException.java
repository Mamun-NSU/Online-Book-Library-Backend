package com.mamun.onlinebooklibrary.exception;


public class EmailException extends RuntimeException {
    private static final String MESSAGE = "Error ! Email already exists!";

    public EmailException() {
        super(MESSAGE);
    }
}