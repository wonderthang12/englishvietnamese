package com.example.english.exception;

public class NotFoundEntityException extends BaseException {
    private final static int ERROR_CODE = 1000;

    public NotFoundEntityException(String message) {
        super(ERROR_CODE, message);
    }

    public NotFoundEntityException(String message, Throwable cause) {
        super(ERROR_CODE, message, cause);
    }
}
