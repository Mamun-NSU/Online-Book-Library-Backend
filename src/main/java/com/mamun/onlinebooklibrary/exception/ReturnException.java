package com.mamun.onlinebooklibrary.exception;

public class ReturnException extends RuntimeException {
    private static final String MESSAGE = "Error! The user did not borrow this book!";

    public ReturnException() {
        super(MESSAGE);
    }
}

