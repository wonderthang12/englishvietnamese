package com.example.english.dto;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BaseCategoryDTO extends BaseDTO {
    private String value;
    private String name;

    @Builder
    public BaseCategoryDTO(Long id, Boolean active, String value, String name) {
        super(id, active);
        this.value = value;
        this.name = name;
    }
}
