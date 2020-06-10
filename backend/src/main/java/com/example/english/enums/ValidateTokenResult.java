package com.example.english.enums;

public enum ValidateTokenResult {
    SUCCESS(0),
    INVALID(1),
    EXPIRATED(2);

    private final Integer value;

    ValidateTokenResult(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }
}
