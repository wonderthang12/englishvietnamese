package com.example.english.service;

import com.example.english.dto.EnglishVietnameseDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface EnglishVietnameseService extends BaseService<EnglishVietnameseDTO> {
    Integer readBooksFromExcelFile(MultipartFile file) throws IOException, org.apache.poi.openxml4j.exceptions.InvalidFormatException;

    EnglishVietnameseDTO findByNewword(String newword);
}
