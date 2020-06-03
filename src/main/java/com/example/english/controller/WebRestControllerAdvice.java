package com.example.english.controller;

import com.example.english.dto.ResponseMsg;
import com.example.english.exception.BaseException;
import com.example.english.exception.DataException;
import com.example.english.msg.Msg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

@RestControllerAdvice
public class WebRestControllerAdvice {
    private final static Logger logger = LoggerFactory.getLogger(WebRestControllerAdvice.class);

    @ExceptionHandler(Exception.class)
    public ResponseMsg handleBaseException(Exception ex) {
        return new ResponseMsg(ex);
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseMsg handleNoHandlerFoundException(Exception ex) {
        return new ResponseMsg(new BaseException(404, ex));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseMsg handleHttpMessageNotReadableException(Exception ex) {
        Throwable throwable = ex.getCause();

        while (throwable != null && throwable.getCause() != null) {
            throwable = throwable.getCause();
        }

        if (throwable == null) {
            return new ResponseMsg(new DataException.InvalidFormat(Msg.getMessage("invalid.format.data")));
        } else if (throwable instanceof BaseException) {
            return new ResponseMsg((BaseException) throwable);
        } else {
            return new ResponseMsg(new DataException.InvalidFormat(Msg.getMessage("invalid.format.data")));
        }
    }

    @ExceptionHandler(HttpMessageConversionException.class)
    public ResponseMsg handleHttpMessageConversionException(Exception ex) {
        Throwable throwable = ex.getCause();

        while (throwable != null && throwable.getCause() != null) {
            throwable = throwable.getCause();
        }

        if (throwable == null) {
            return new ResponseMsg(new BaseException(502, ex));
        } else if (throwable instanceof BaseException) {
            return new ResponseMsg((BaseException) throwable);
        } else {
            return new ResponseMsg(new BaseException(502, throwable));
        }
    }

    @ExceptionHandler(BindException.class)
    public ResponseMsg handleBindException(Exception ex) {
        Throwable throwable = ex.getCause();

        while (throwable != null && throwable.getCause() != null) {
            throwable = throwable.getCause();
        }

        if (throwable == null) {
            return new ResponseMsg(new BaseException(503, ex));
        } else if (throwable instanceof BaseException) {
            return new ResponseMsg((BaseException) throwable);
        } else {
            return new ResponseMsg(new BaseException(503, throwable));
        }
    }
}
