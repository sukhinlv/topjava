package ru.javawebinar.topjava.model;

import java.time.LocalDateTime;

public class MealTo {
    private final int id;
    private final LocalDateTime dateTime;
    private final String description;
    private final int calories;
    private final boolean excess;

    public MealTo(int id, LocalDateTime dateTime, String description, int calories, boolean excess) {
        this.id = id;
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
        this.excess = excess;
    }

    @SuppressWarnings("unused")
    public LocalDateTime getDateTime() {
        return dateTime;
    }

    @SuppressWarnings("unused")
    public String getDescription() {
        return description;
    }

    @SuppressWarnings("unused")
    public int getCalories() {
        return calories;
    }

    public int getId() {
        return id;
    }

    @SuppressWarnings("unused")
    public boolean isExcess() {
        return excess;
    }

    @Override
    public String toString() {
        return "MealTo{" +
                "id=" + id +
                ", dateTime=" + dateTime +
                ", description='" + description + '\'' +
                ", calories=" + calories +
                '}';
    }
}
