package com.example.english.util;

import com.example.english.exception.BaseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public final class ObjectMapperUtil {
    private final static Logger logger = LoggerFactory.getLogger(ObjectMapperUtil.class);

    public final static ObjectMapper mapper = new ObjectMapper() {{
        configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        registerModule(new JavaTimeModule());
    }}; // jackson's objectmapper

    public final static <T> T convertValue(Map<String, Object> fromValue, Class<T> toValueType) {
        if (fromValue == null) {
            return null;
        }

        return mapper.convertValue(fromValue, toValueType);
    }

    public final static <T> T convertValue(String fromValue, Class<T> toValueType) {
        if (fromValue == null) {
            return null;
        }

        try {
            return mapper.readValue(fromValue, toValueType);
        } catch (IOException e) {
            logger.error("Cannot convert string to object: " + fromValue, e);
            throw new BaseException("Cannot convert string to object");
        }
    }

    public final static Map<String, Object> convertValue(Object fromValue) {
        if (fromValue == null) {
            return null;
        }

        return mapper.convertValue(fromValue, Map.class);
    }

    public final static String writeValueAsString(Object fromValue) {
        if (fromValue == null) {
            return null;
        }

        try {
            return mapper.writeValueAsString(fromValue);
        } catch (JsonProcessingException e) {
            logger.error("Cannot write value as string", e);
            throw new BaseException("Cannot write value as string");
        }
    }

    public final static <T> List<T> convertValues(List<Map<String, Object>> fromValue, Class<T> toValueType) {
        if (fromValue == null) {
            return new ArrayList<>();
        }

        List<T> toList = new ArrayList<>();
        for (Map<String, Object> map : fromValue) {
            toList.add(convertValue(map, toValueType));
        }

        return toList;
    }

    public final static List<Map<String, Object>> convertValues(List<Object> fromValue) {
        if (fromValue == null) {
            return new ArrayList<>();
        }

        List<Map<String, Object>> toList = new ArrayList<>();
        for (Object obj : fromValue) {
            toList.add(convertValue(obj));
        }

        return toList;
    }
}
