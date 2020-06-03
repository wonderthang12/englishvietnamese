package com.example.english.controller;

import com.example.english.dto.EnglishVietnameseDTO;
import com.example.english.dto.ResponseMsg;
import com.example.english.service.EnglishVietnameseService;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping(path = "/english-vietnamese")
public class EnglishVietnameseController extends BaseController<EnglishVietnameseDTO, EnglishVietnameseService> {
    private final static Logger logger = LoggerFactory.getLogger(EnglishVietnameseController.class);

    @Autowired
    private EnglishVietnameseService service;

    @Override
    protected EnglishVietnameseService getService() {
        return service;
    }

    @Override
    protected Logger getLogger() {
        return logger;
    }

    @PostMapping("/file")
    public ResponseEntity<ResponseMsg> uploadFile(@RequestParam("file") MultipartFile file) throws IOException, InvalidFormatException {
        return response(service.readBooksFromExcelFile(file));
    }

    @GetMapping("/newword")
    public ResponseEntity<ResponseMsg> findByNewword(@RequestParam("newword") String newword){
        return response(service.findByNewword(newword));
    }
}
