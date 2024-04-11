package com.example.springtraining.constant;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class DateTimeConverter {
    private final static DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    
    private final static DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
    
    public static String formatLocalDate(LocalDateTime date) {
        return date != null ? date.format(dateFormatter) : null;
    }

    public static String formatLocalTime(LocalTime time) {
        return time != null ? time.format(timeFormatter) : null;
    }
}
