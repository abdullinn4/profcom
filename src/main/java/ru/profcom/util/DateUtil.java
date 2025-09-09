package ru.profcom.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateUtil {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    public static String format(LocalDate date) {
        return date != null ? date.format(FORMATTER) : null;
    }

    public static LocalDate parse(String str) {
        return str != null && !str.isBlank() ? LocalDate.parse(str, FORMATTER) : null;
    }
}
