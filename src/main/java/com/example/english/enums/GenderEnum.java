package com.example.english.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.stream.Stream;

public enum GenderEnum implements IEnum {
    MALE(0), FEMALE(1);

    private Integer value;

    private GenderEnum(Integer value) {
        this.value = value;
    }

    @Override
    public Integer getValue() {
        return this.value;
    }

    @JsonCreator
    static GenderEnum fromValue(int value){
        return Stream.of(GenderEnum.values())
                .filter(c -> c.getValue().equals(value))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
