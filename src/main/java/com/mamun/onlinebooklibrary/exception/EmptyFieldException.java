package com.mamun.onlinebooklibrary.exception;

public class EmptyFieldException extends RuntimeException {
    private static final String MESSAGE = "Error! Valid ";

    public EmptyFieldException(String fieldName) {
        super(MESSAGE + fieldName + " must be provided and can not be empty!");
    }
}
