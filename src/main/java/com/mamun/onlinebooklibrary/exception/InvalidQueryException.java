package com.mamun.onlinebooklibrary.exception;

public class InvalidQueryException extends Exception {
    private static final String MESSAGE = "Error! Invalid Query, ";

    public InvalidQueryException(String argument) {
        super(MESSAGE + argument + " can not be negative!");
    }
}
