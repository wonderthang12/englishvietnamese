package com.example.english.dto;

import com.example.english.enums.LevelEnum;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class LevelDTO {
    private Boolean active;

    private LevelEnum level;
}
