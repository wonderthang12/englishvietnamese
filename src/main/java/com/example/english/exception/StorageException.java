package com.example.english.exception;

public class StorageException {
    private final static int ERROR_CODE = 610;

    public static class InvalidMultipartFile extends BaseException {

        public InvalidMultipartFile(String message) {
            super(ERROR_CODE + 0, message);
        }

        public InvalidMultipartFile(String message, Throwable cause) {
            super(ERROR_CODE + 0, message, cause);
        }
    }

    public static class StoreFileException extends BaseException {

        public StoreFileException(String message) {
            super(ERROR_CODE + 1, message);
        }

        public StoreFileException(String message, Throwable cause) {
            super(ERROR_CODE + 1, message, cause);
        }
    }

    public static class FileNotFound extends BaseException {

        public FileNotFound(String message) {
            super(ERROR_CODE + 2, message);
        }

        public FileNotFound(String message, Throwable cause) {
            super(ERROR_CODE + 2, message, cause);
        }
    }
}
