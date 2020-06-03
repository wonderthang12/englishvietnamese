package com.example.english.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public interface IEnum {
    @JsonValue
    Integer getValue();
}
