package com.example.english.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Locale;

public final class Util {
    private final static Logger logger = LoggerFactory.getLogger(Util.class);

    public static Locale getLocaleDefault() {
        return new Locale("vi", "VI");
    }
}
