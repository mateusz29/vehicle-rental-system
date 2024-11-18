package org.example.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateUtils {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static LocalDate convertStringToLocalDate(String dateString) {
        try {
            return LocalDate.parse(dateString, FORMATTER);
        } catch (Exception e) {
            return null;
        }
    }
}
