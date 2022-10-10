package ru.javawebinar.topjava.storage;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import static org.slf4j.LoggerFactory.getLogger;

public class MemoryMealStorage implements Storage<Meal, Integer> {
    private final Map<Integer, Meal> storage = new ConcurrentHashMap<>();
    private final AtomicInteger idCounter = new AtomicInteger(0);
    private final Logger log = getLogger(MemoryMealStorage.class);

    public MemoryMealStorage() {
        // TODO remove hardcoded storage initialization
        save(new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500));
        save(new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000));
        save(new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500));
        save(new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100));
        save(new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000));
        save(new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500));
        save(new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410));
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
        int newId = (entity.getId() == null) ? idCounter.incrementAndGet() : entity.getId();
        Meal newMeal = new Meal(newId, entity.getDateTime(), entity.getDescription(), entity.getCalories());
        log.debug("save meal {}", newMeal);
        return storage.merge(newId, newMeal, (mealIn, mealOut) -> mealOut);
    }
}
