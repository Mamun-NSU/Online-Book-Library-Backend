package com.mamun.onlinebooklibrary.exception;

public class IntegrityException extends Exception {
    private static final String MESSAGE = "Duplicate Entry Error! ";

    public IntegrityException(String field) {
        super(MESSAGE + field + " already exists!");
    }
}
