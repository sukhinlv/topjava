package ru.javawebinar.topjava.util.formatter;

import org.springframework.format.Formatter;

import java.text.ParseException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class CustomTimeFormatter implements Formatter<LocalTime> {
    @Override
    public LocalTime parse(String text, Locale locale) throws ParseException {
        return LocalTime.parse(text, DateTimeFormatter.ofPattern("HH:mm", locale));
    }

    @Override
    public String print(LocalTime localDate, Locale locale) {
        return localDate.format(DateTimeFormatter.ofPattern("HH:mm", locale));
    }
}
