package com.example.english.util;

import com.example.english.dto.ResponseMsg;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ResponseUtil {
    public final static void writeResponse(HttpServletResponse response, ResponseMsg responseMsg) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().write(ObjectMapperUtil.writeValueAsString(responseMsg));
    }
}
