package com.example.english.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.stream.Stream;

public enum UserTypeEnum implements IEnum {
    ADMIN(0), STUDENT(1);

    private Integer value;

    private UserTypeEnum(Integer value) {
        this.value = value;
    }

    @Override
    public Integer getValue() {
        return this.value;
    }

    @JsonCreator
    static UserTypeEnum fromValue(int value){
        return Stream.of(UserTypeEnum.values())
                .filter(c -> c.getValue().equals(value))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
