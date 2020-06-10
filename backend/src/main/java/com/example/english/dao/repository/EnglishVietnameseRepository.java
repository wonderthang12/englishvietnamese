package com.example.english.dao.repository;

import com.example.english.dao.model.EnglishVietnameseEntity;
import com.example.english.dto.EnglishVietnameseDTO;
import org.springframework.stereotype.Repository;

@Repository
public interface EnglishVietnameseRepository extends BaseRepository<EnglishVietnameseEntity, EnglishVietnameseDTO, Long> {

    EnglishVietnameseEntity findEnglishVietnameseEntityByNewword(String newword);
}
