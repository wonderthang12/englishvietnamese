package com.example.english.exception;

public class DataException {
    private final static int ERROR_CODE = 600;

    public static class NotFoundEntity extends BaseException {

        public NotFoundEntity(String message) {
            super(ERROR_CODE + 0, message);
        }

        public NotFoundEntity(String message, Throwable cause) {
            super(ERROR_CODE + 0, message, cause);
        }
    }

    public static class InvalidFormat extends BaseException {

        public InvalidFormat(String message) {
            super(ERROR_CODE + 1, message);
        }

        public InvalidFormat(String message, Throwable cause) {
            super(ERROR_CODE + 1, message, cause);
        }
    }

    public static class InvalidDateFormat extends BaseException {

        public InvalidDateFormat(String message) {
            super(ERROR_CODE + 2, message);
        }

        public InvalidDateFormat(String message, Throwable cause) {
            super(ERROR_CODE + 2, message, cause);
        }
    }

    public static class InvalidDataType extends BaseException {

        public InvalidDataType(String message) {
            super(ERROR_CODE + 3, message);
        }

        public InvalidDataType(String message, Throwable cause) {
            super(ERROR_CODE + 3, message, cause);
        }
    }
}
