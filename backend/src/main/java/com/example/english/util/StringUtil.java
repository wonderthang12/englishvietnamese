package com.example.english.util;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.Normalizer;
import java.util.Arrays;
import java.util.regex.Pattern;

public class StringUtil {
    private final static Logger logger = LoggerFactory.getLogger(StringUtil.class);

    public final static String deleteAccents(String text) {
        String nfdNormalizedString = Normalizer.normalize(text, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        text = pattern.matcher(nfdNormalizedString).replaceAll("")
                .replaceAll("Đ", "D")
                .replaceAll("đ", "d");

        return text;
    }

    public final static String cleanNonVisibleCharacters(String text) {
        if (text == null) {
            return null;
        }

        return text.replaceAll("\\s+","");
    }

    public final static String toString(final String... source) {
        if (source == null || source.length == 0) {
            return null;
        }
        return Arrays
                .stream(source)
                .reduce((a, b) -> a.concat(",").concat(b))
                .get();
    }

    public final static String[] toArray(final String source) {
        if (StringUtils.isBlank(source)) {
            return null;
        }

        return source.split(",");
    }
}
