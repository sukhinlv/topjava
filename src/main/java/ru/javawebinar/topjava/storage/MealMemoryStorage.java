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

public class MealMemoryStorage implements Storage<Meal, Integer> {
    private final Map<Integer, Meal> storage = new ConcurrentHashMap<>();
    private final AtomicInteger idCounter = new AtomicInteger(0);
    private final Logger log = getLogger(MealMemoryStorage.class);

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
        Integer newId = (entity.getId() == null) ? idCounter.incrementAndGet() : entity.getId();
        Meal newMeal = new Meal(newId, entity.getDateTime(), entity.getDescription(), entity.getCalories());
        log.debug("save meal {}", newMeal);
        return storage.put(newId, newMeal);
    }
}
