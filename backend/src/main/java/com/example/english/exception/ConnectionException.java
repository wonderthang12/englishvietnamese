package com.example.english.exception;

public class ConnectionException {
    private final static int ERROR_CODE = 620;

    public static class ExternalServerError extends BaseException {

        public ExternalServerError(String message) {
            super(ERROR_CODE + 0, message);
        }

        public ExternalServerError(String message, Throwable cause) {
            super(ERROR_CODE + 0, message, cause);
        }
    }
}
