package com.example.english.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.stream.Stream;

public enum LevelEnum implements IEnum {
    LEVEL1(1), LEVEL2(2), LEVEL3(3);

    private Integer value;

    private LevelEnum(Integer value) {
        this.value = value;
    }

    @Override
    public Integer getValue() {
        return this.value;
    }

    @JsonCreator
    static LevelEnum fromValue(int value){
        return Stream.of(LevelEnum.values())
                .filter(c -> c.getValue().equals(value))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }

}
