package com.example.english.controller;

import com.example.english.dto.BaseDTO;
import com.example.english.dto.ResponseMsg;
import com.example.english.service.BaseService;
import org.slf4j.Logger;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

public abstract class BaseController<Y extends BaseDTO, S extends BaseService> extends BaseResponseController {
    protected abstract S getService();
    protected abstract Logger getLogger();

    @GetMapping("/{id}")
    @Transactional
    public ResponseEntity<ResponseMsg> getById(@PathVariable Long id) {
        return response(getService().findById(id));
    }

    @GetMapping(params = {"page"})
    @Transactional
    public ResponseEntity<ResponseMsg> search(Y dto, @PageableDefault(value = 200, size = 200, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        return response(getService().search(dto, pageable));
    }

    @PostMapping
    @Transactional
    public ResponseEntity<ResponseMsg> create(@Valid @RequestBody Y dto) {
        return response(getService().create(dto));
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<ResponseMsg> update(@PathVariable Long id, @Valid @RequestBody Y dto) {
        return response(getService().update(id, dto));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<ResponseMsg> delete(@PathVariable Long id) {
        getService().delete(id);
        return response(null);
    }
}
