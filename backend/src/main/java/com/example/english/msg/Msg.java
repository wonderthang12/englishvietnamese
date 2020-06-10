package com.example.english.msg;

import com.example.english.util.Util;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

public class Msg {
    private static ReloadableResourceBundleMessageSource messageSource;

    private static MessageSource getMessageSource() {
        if (messageSource != null) {
            return messageSource;
        }

        messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:i18n/messages");
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }

    public static String getMessage(String key, Object[] args, Locale locale, HttpServletRequest request) {
        if (request != null) {
            String headerLang = request.getHeader("Accept-Language");
            if (headerLang == null) {
                locale = Util.getLocaleDefault();
            }
        }

        if (locale == null) {
            locale = LocaleContextHolder.getLocale();
        }

        return getMessageSource().getMessage(key, args, key, locale);
    }

    public static String getMessage(String key, Object[] args, Locale locale) {
        return getMessage(key, args, locale, null);
    }

    public static String getMessage(String key, Object[] args, HttpServletRequest request) {
        return getMessage(key, args, null, request);
    }

    public static String getMessage(String key, Object[] args) {
        return getMessage(key, args, null, null);
    }

    public static String getMessage(String key) {
        return getMessage(key, null, null, null);
    }
}
