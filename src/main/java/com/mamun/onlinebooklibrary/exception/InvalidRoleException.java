package com.mamun.onlinebooklibrary.exception;

public class InvalidRoleException extends RuntimeException {
    private static final String MESSAGE = "Error! Role should be either CUSTOMER or ADMIN";

    public InvalidRoleException() {
        super(MESSAGE);
    }
}
