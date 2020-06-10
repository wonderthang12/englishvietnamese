package com.example.english.controller;

import com.example.english.dto.ResponseMsg;
import com.example.english.exception.StorageFileNotFoundException;
import com.example.english.msg.Msg;
import com.example.english.util.ObjectMapperUtil;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;

public abstract class BaseResponseController {
    protected abstract Logger getLogger();

    protected ResponseEntity response(int code, String message, Object data) {
        ResponseMsg responseMsg = new ResponseMsg(code, message, data);

        if (data instanceof Page) {
            Page page = (Page) data;
            responseMsg.setTotalPages(page.getTotalPages());
            responseMsg.setTotalElements(page.getTotalElements());
            responseMsg.setPageSize(page.getSize());
            responseMsg.setPageNumber(page.getNumber());
            responseMsg.setNumberOfElements(page.getNumberOfElements());
            responseMsg.setData(page.getContent());
        }
        getLogger().debug("Response: " + ObjectMapperUtil.writeValueAsString(responseMsg));
        return new ResponseEntity(responseMsg, HttpStatus.OK);
    }

    protected ResponseEntity responseAttachment(String fileName) {
        return responseAttachment(new File(fileName));
    }

    protected ResponseEntity responseAttachment(File file) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Disposition", "attachment; filename=" + file.getName());

            return new ResponseEntity(new InputStreamResource(new ByteArrayInputStream(FileUtils.readFileToByteArray(file))), headers, HttpStatus.OK);
        } catch (IOException e) {
            throw new StorageFileNotFoundException(Msg.getMessage("failed.to.read.file", new Object[] {file.getName()}));
        }
    }

    protected ResponseEntity response(String message, Object data) {
        return response(0, message , data);
    }

    protected ResponseEntity response(int code, String message) {
        return response(code, message, null);
    }

    protected ResponseEntity response(Object data) {
        return response(null, data);
    }

    protected ResponseEntity response() {
        return response(null);
    }
}
