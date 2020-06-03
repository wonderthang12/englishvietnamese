package com.example.english.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.stream.Stream;

public enum BlockEnum implements IEnum {
    UNBLOCKED(0), BLOCKED(1);

    private Integer value;

    private BlockEnum(Integer value) {
        this.value = value;
    }

    @Override
    public Integer getValue() {
        return this.value;
    }

    @JsonCreator
    static BlockEnum fromValue(int value){
        return Stream.of(BlockEnum.values())
                .filter(c -> c.getValue().equals(value))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
