package com.example.english.service;

import com.example.english.dto.BaseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BaseService<Y extends BaseDTO> {
    Y create(Y dto);

    Y update(Long id, Y dto);

    void delete(Long id);

    Y findById(Long id);

    boolean existsById(Long id);

    Page<Y> search(Y dto, Pageable pageable);
}
