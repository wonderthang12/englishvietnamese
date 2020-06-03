package com.example.english.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public final class DateUtil {
    private final static Logger logger = LoggerFactory.getLogger(DateUtil.class);

    public final static LocalDate getMinDate() {
        return truncateYear(LocalDate.now()).withYear(1870);
    }

    public final static LocalDate getMaxDate() {
        return truncateYear(LocalDate.now()).plusYears(100);
    }

    public final static LocalDate truncateYear(LocalDate date) {
        return truncateMonth(date).withMonth(1);
    }

    public final static LocalDate truncateMonth(LocalDate date) {
        return date.withDayOfMonth(1);
    }

    public final static ZonedDateTime truncateDate(ZonedDateTime date) {
        return date.truncatedTo(ChronoUnit.DAYS);
    }

    public final static ZonedDateTime truncateHour(ZonedDateTime date) {
        return date.truncatedTo(ChronoUnit.HOURS);
    }

    public final static ZonedDateTime getNow() {
        return truncateSecond(ZonedDateTime.now());
    }

    public final static LocalDate getToday() {
        return LocalDate.now();
    }

    public final static ZonedDateTime truncateMinute(ZonedDateTime date) {
        return date.truncatedTo(ChronoUnit.MINUTES);
    }

    public final static ZonedDateTime truncateSecond(ZonedDateTime date) {
        return date.truncatedTo(ChronoUnit.SECONDS);
    }

    public final static boolean isValidDate(LocalDateTime date) {
        if (date == null) {
            return false;
        }

        return isValidDate(date.toLocalDate());
    }

    public final static boolean isValidDate(LocalDate date) {
        if (date == null) {
            return false;
        }

        LocalDate minDate = getMinDate();
        LocalDate maxDate = getMaxDate();
        if (date.compareTo(minDate) >= 0 && date.compareTo(maxDate) <= 0) {
            return true;
        }

        return false;
    }

    public final static int truncatedHourCompareTo(ZonedDateTime date1, ZonedDateTime date2) {
        return truncateHour(date1).compareTo(truncateHour(date2));
    }

    public final static int compareTo(LocalDate date1, LocalDate date2) {
        return date1.compareTo(date2);
    }

    public final static LocalDateTime parseValidDateTime(String dateStr) {
        return parseValidDateTime(dateStr, null);
    }

    public final static LocalDate parseValidDate(String dateStr) {
        return parseValidDate(dateStr, null);
    }

    public final static LocalDateTime parseValidDateTime(String dateStr, String parsePattern) {
        if (dateStr == null) {
            return null;
        }

        LocalDateTime dateTime;

        if (parsePattern != null) {
            dateTime = parseDateTime(dateStr, parsePattern);

            if (isValidDate(dateTime)) {
                return dateTime;
            }

            return null;
        }


        Map<String, String> timePatterns = new HashMap<>();
        timePatterns.put("T([0-1][0-9]|2[0-4]):[0-5][0-9]:[0-5][0-9]", "'T'HH:mm:ss");
        timePatterns.put(" ([0-1][0-9]|2[0-4]):[0-5][0-9]:[0-5][0-9]", " HH:mm:ss");
        timePatterns.put("T([0-1][0-9]|2[0-4]):[0-5][0-9]", "'T'HH:mm");
        timePatterns.put(" ([0-1][0-9]|2[0-4]):[0-5][0-9]", " HH:mm");

        Map<String, String> datePatterns = getDatePattern();

        for (String timePattern : timePatterns.keySet()) {
            for (String datePattern : datePatterns.keySet()) {
                String pattern = "^" + datePattern + timePattern + "$";

                if (!dateStr.matches(pattern)) {
                    continue;
                }

                dateTime = parseDateTime(dateStr, datePatterns.get(datePattern) + timePatterns.get(timePattern));
                if (isValidDate(dateTime)) {
                    return dateTime;
                }
            }
        }

        return getLocalDateTime(parseValidDate(dateStr));
    }

    public final static LocalDate parseValidDate(String dateStr, String parsePattern) {
        if (dateStr == null) {
            return null;
        }

        LocalDate date;

        if (parsePattern != null) {
            date = parse(dateStr, parsePattern);

            if (isValidDate(date)) {
                return date;
            }

            return null;
        }

        Map<String, String> datePatterns = getDatePattern();

        for (String datePattern : datePatterns.keySet()) {
            String pattern = "^" + datePattern + "$";

            if (!dateStr.matches(pattern)) {
                continue;
            }

            date = parse(dateStr, datePatterns.get(datePattern));
            if (isValidDate(date)) {
                return date;
            }
        }

        return null;
    }

    public final static LocalDateTime parseDateTime(String d, String format) {
        if (d == null || format == null) {
            return null;
        }

        DateTimeFormatter formatter = new DateTimeFormatterBuilder()
                .appendPattern(format)
                .parseDefaulting(ChronoField.MONTH_OF_YEAR, 1)
                .parseDefaulting(ChronoField.DAY_OF_MONTH, 1)
                .toFormatter();

        return LocalDateTime.parse(d, formatter);
    }

    public final static LocalDate parse(String d, String format) {
        if (d == null || format == null) {
            return null;
        }

        DateTimeFormatter formatter = new DateTimeFormatterBuilder()
                .appendPattern(format)
                .parseDefaulting(ChronoField.MONTH_OF_YEAR, 1)
                .parseDefaulting(ChronoField.DAY_OF_MONTH, 1)
                .toFormatter();

        return LocalDate.parse(d, formatter);
    }

    public final static String format(LocalDate d, String format) {
        if (d == null || format == null) {
            return null;
        }
        return DateTimeFormatter.ofPattern(format).format(d);
    }

    public final static String format(LocalDateTime d, String format) {
        if (d == null || format == null) {
            return null;
        }
        return DateTimeFormatter.ofPattern(format).format(d);
    }

    public static String format(ZonedDateTime d, String format) {
        if (d == null || format == null) {
            return null;
        }
        return DateTimeFormatter.ofPattern(format).format(d);
    }

    public final static String formatDateTime(LocalDateTime d) {
        return format(d, "yyyy-MM-dd'T'HH:mm:ss");
    }

    public final static String formatDate(LocalDate d) {
        return format(d, "yyyy-MM-dd");
    }

    public final static String formatYear(LocalDate d) {
        return format(d, "yyyy");
    }

    public final static Date getDate(ZonedDateTime dt) {
        if (dt == null) {
            return null;
        }

        return Date.from(dt.toInstant());
    }

    public final static Date getDate(LocalDate ld) {
        if (ld == null) {
            return null;
        }

        return Date.from(ld.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    public final static LocalDate getLocalDate(Date dt) {
        if (dt == null) {
            return null;
        }

        return dt.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    public final static ZonedDateTime getZonedDateTime(LocalDateTime dt) {
        if (dt == null) {
            return null;
        }

        return dt.atZone(ZoneId.systemDefault());
    }

    public final static ZonedDateTime getZonedDateTime(Date dt) {
        if (dt == null) {
            return null;
        }

        return dt.toInstant().atZone(ZoneId.systemDefault());
    }

    public final static ZonedDateTime getZonedDateTime(LocalDate ld) {
        if (ld == null) {
            return null;
        }

        return ld.atStartOfDay(ZoneId.systemDefault());
    }

    public final static LocalDateTime getLocalDateTime(LocalDate ld) {
        if (ld == null) {
            return null;
        }

        return ld.atStartOfDay();
    }

    private final static Map<String, String> getDatePattern() {
        String yyyyPattern = "[1-2][0-9]{3}";
        String yyyy = "yyyy";
        String ddPattern = "[0-3][0-9]";
        String dd = "dd";
        String dPattern = "[1-9]";
        String d = "d";
        String mmPattern = "(0[1-9]|1[0-2])";
        String mm = "MM";
        String mPattern = "[1-9]";
        String m = "M";
        String yyPattern = "[0-9]{2}";
        String yy = "yy";

        String[] separateChars = {"", "/", "-", "\\."};

        Map<String, String> parsePatterns = new HashMap<>();

        for (String separateChar : separateChars) {
            parsePatterns.put(yyyyPattern, yyyy);// yyyy
            parsePatterns.put(yyyyPattern + separateChar + mmPattern + separateChar + ddPattern, yyyy + separateChar + mm + separateChar + dd);// yyyy/MM/dd

            parsePatterns.put(ddPattern + separateChar + mmPattern + separateChar + yyyyPattern, dd + separateChar + mm + separateChar + yyyy);// dd/MM/yyyy
            parsePatterns.put(dPattern + separateChar + mmPattern + separateChar + yyyyPattern, d + separateChar + mm + separateChar + yyyy);// d/MM/yyyy
            parsePatterns.put(ddPattern + separateChar + mPattern + separateChar + yyyyPattern, dd + separateChar + m + separateChar + yyyy);// dd/M/yyyy
            parsePatterns.put(dPattern + separateChar + mPattern + separateChar + yyyyPattern, d + separateChar + m + separateChar + yyyy);// d/M/yyyy
            parsePatterns.put(mmPattern + separateChar + yyyyPattern, mm + separateChar + yyyy);// MM/yyyy
            parsePatterns.put(mPattern + separateChar + yyyyPattern, m + separateChar + yyyy);// M/yyyy

            parsePatterns.put(ddPattern + separateChar + mmPattern + separateChar + yyPattern, dd + separateChar + mm + separateChar + yy);// dd/MM/yy
            parsePatterns.put(dPattern + separateChar + mmPattern + separateChar + yyPattern, d + separateChar + mm + separateChar + yy);// d/MM/yy
            parsePatterns.put(ddPattern + separateChar + mPattern + separateChar + yyPattern, dd + separateChar + m + separateChar + yy);// dd/M/yy
            parsePatterns.put(dPattern + separateChar + mPattern + separateChar + yyPattern, d + separateChar + m + separateChar + yy);// d/M/yy
            parsePatterns.put(mmPattern + separateChar + yyPattern, mm + separateChar + yy);// MM/yy
            parsePatterns.put(mPattern + separateChar + yyPattern, m + separateChar + yy);// M/yy
        }

        return parsePatterns;
    }
}
