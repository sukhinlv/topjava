package ru.javawebinar.topjava.util.formatter;

import org.springframework.format.AnnotationFormatterFactory;
import org.springframework.format.Parser;
import org.springframework.format.Printer;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CustomDateFormatAnnotationFormatterFactory implements AnnotationFormatterFactory<CustomDateFormat> {
    @Override
    public Set<Class<?>> getFieldTypes() {
        return new HashSet<>(List.of(LocalDate.class));
    }

    @Override
    public Printer<?> getPrinter(CustomDateFormat annotation, Class<?> fieldType) {
        return getAddressFormatter(annotation, fieldType);
    }

    @Override
    public Parser<?> getParser(CustomDateFormat annotation, Class<?> fieldType) {
        return getAddressFormatter(annotation, fieldType);
    }

    private CustomDateFormatter getAddressFormatter(CustomDateFormat annotation, Class<?> fieldType) {
        CustomDateFormatter formatter = new CustomDateFormatter();
        return formatter;
    }
}
