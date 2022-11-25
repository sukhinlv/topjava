package ru.javawebinar.topjava.util.formatter;

import org.springframework.format.AnnotationFormatterFactory;
import org.springframework.format.Parser;
import org.springframework.format.Printer;

import java.time.LocalTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CustomTimeFormatAnnotationFormatterFactory implements AnnotationFormatterFactory<CustomTimeFormat> {
    @Override
    public Set<Class<?>> getFieldTypes() {
        return new HashSet<>(List.of(LocalTime.class));
    }

    @Override
    public Printer<?> getPrinter(CustomTimeFormat annotation, Class<?> fieldType) {
        return getAddressFormatter(annotation, fieldType);
    }

    @Override
    public Parser<?> getParser(CustomTimeFormat annotation, Class<?> fieldType) {
        return getAddressFormatter(annotation, fieldType);
    }

    private CustomTimeFormatter getAddressFormatter(CustomTimeFormat annotation, Class<?> fieldType) {
        CustomTimeFormatter formatter = new CustomTimeFormatter();
        return formatter;
    }
}
