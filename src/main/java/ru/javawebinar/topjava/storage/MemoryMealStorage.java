package ru.javawebinar.topjava.storage;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import static org.slf4j.LoggerFactory.getLogger;
import static ru.javawebinar.topjava.util.MealsUtil.getMealFromEntityAndId;

public class MemoryMealStorage implements Storage<Meal, Integer> {
    private final Map<Integer, Meal> storage = new ConcurrentHashMap<>();
    private final AtomicInteger idCounter = new AtomicInteger(0);
    private final Logger log = getLogger(MemoryMealStorage.class);

    public MemoryMealStorage() {
        // TODO remove hardcoded storage initialization
        List<Meal> mealList = Arrays.asList(
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410)
        );

        for (Meal meal : mealList) {
            save(meal);
        }
    }

    @Override
    public void deleteById(Integer id) {
        log.debug("deleteById {}", id);
        storage.remove(id);
    }

    @Override
    public Optional<Meal> findById(Integer id) {
        log.debug("findById {}", id);
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public List<Meal> findAll() {
        log.debug("findAll");
        return new ArrayList<>(storage.values());
    }

    @Override
    public Meal save(Meal entity) {
        log.debug("save meal {}", entity);
        if (entity.getId() != null) {
            return storage.computeIfPresent(entity.getId(), (id, meal) -> getMealFromEntityAndId(entity, id));
        }
        Meal newMeal = getMealFromEntityAndId(entity, idCounter.incrementAndGet());
        storage.put(newMeal.getId(), newMeal);
        return newMeal;
    }
}
