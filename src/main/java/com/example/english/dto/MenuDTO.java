package com.example.english.dto;

import com.example.english.enums.LevelEnum;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
public class MenuDTO extends BaseDTO {
    private String name;

    private Integer location;

    private String link;

    private List<MenuDTO> children;

    private Long parentId;

    private LevelEnum level;
}
