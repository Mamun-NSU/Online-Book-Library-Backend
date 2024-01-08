package com.mamun.onlinebooklibrary.exception;

public class PasswordException extends RuntimeException {
    private static final String MESSAGE = "Error ! Password must be at least 5 characters long!";

    public PasswordException() {
        super(MESSAGE);
    }
}

