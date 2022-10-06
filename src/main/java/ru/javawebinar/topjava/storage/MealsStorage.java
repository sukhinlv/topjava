package ru.javawebinar.topjava.storage;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class MealsStorage implements Storage<Meal, Integer> {
    @Override
    public void deleteAll() {
        // TODO: implement deleteAll()
    }

    @Override
    public void deleteById(Integer id) {
        // TODO: implement deleteById()
    }

    @Override
    public Optional<Meal> findById(Integer id) {
        // TODO: implement findById()
        return Optional.empty();
    }

    @Override
    public List<Meal> findAll() {
        // TODO: implement findAll()
        return new ArrayList<>(Arrays.asList(
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410)
        ));
    }

    @Override
    public void save(Meal entity) {
        // TODO: implement save()
    }
}
