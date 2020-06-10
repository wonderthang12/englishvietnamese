package com.example.english.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class EnglishVietnameseDTO extends BaseDTO {

    private String newword;

    private String category;

    private String spelling;

    private String mean;
}
