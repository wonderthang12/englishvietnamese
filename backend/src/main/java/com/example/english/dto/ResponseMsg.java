package com.example.english.dto;

import com.example.english.exception.BaseException;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Setter
@Getter
@NoArgsConstructor
@ToString
public class ResponseMsg implements Serializable {
    private Integer code = 0;
    private String message = null;
    private Object data = null;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Long totalElements;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Integer totalPages;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Integer pageNumber;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Integer pageSize;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Integer numberOfElements;

    public ResponseMsg(Integer code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public ResponseMsg(Integer code, String message) {
        this(code, message, null);
    }

    public ResponseMsg(Exception ex) {
        this();

        message = ex.getMessage();

        Throwable cause = ex.getCause();
        if (cause != null) {
            message += "(" + cause.getMessage() + ")";
        }

        if (ex instanceof BaseException) {
            BaseException baseException = (BaseException) ex;
            data = baseException.getData();
            code = baseException.getCode();
        } else {
            code = 500;
            message = "Internal Server Error: " + message;
            ex.printStackTrace();
        }
    }
}
