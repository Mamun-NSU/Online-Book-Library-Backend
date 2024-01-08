package com.mamun.onlinebooklibrary.exception;

public class IllegalApiAccessException extends RuntimeException {
    private static final String MESSAGE = "Error ! You are not authorized to use this API!";

    public IllegalApiAccessException() {
        super(MESSAGE);
    }
}
