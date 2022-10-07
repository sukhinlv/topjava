package ru.javawebinar.topjava.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Meal {
    public static final Meal EMPTY = new Meal();
    private Integer id;
    private LocalDateTime dateTime;

    private String description;

    private int calories;

    public Meal() {
    }

    public Meal(Integer id, LocalDateTime dateTime, String description, int calories) {
        this.id = id;
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
    }

    public Meal(LocalDateTime dateTime, String description, int calories) {
        this(0, dateTime, description, calories);
    }

    public int getCalories() {
        return calories;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getDescription() {
        return description;
    }

    public LocalDate getDate() {
        return dateTime.toLocalDate();
    }

    @SuppressWarnings("unused")
    public Integer getId() {
        return id;
    }

    public LocalTime getTime() {
        return dateTime.toLocalTime();
    }

    @Override
    public String toString() {
        return "Meal{" +
                "id=" + id +
                ", dateTime=" + dateTime +
                ", description='" + description + '\'' +
                ", calories=" + calories +
                '}';
    }
}
