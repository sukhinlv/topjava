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

public class MealArrayStorage implements Storage<Meal, Integer> {
    private final Map<Integer, Meal> storage = new ConcurrentHashMap<>();
    private final AtomicInteger idCounter;
    private final Logger log = getLogger(MealArrayStorage.class);

    private MealArrayStorage() {
        // TODO remove hardcoded storage initialization
        storage.put(1, new Meal(1, LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500));
        storage.put(2, new Meal(2, LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000));
        storage.put(3, new Meal(3, LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500));
        storage.put(4, new Meal(4, LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100));
        storage.put(5, new Meal(5, LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000));
        storage.put(6, new Meal(6, LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500));
        storage.put(7, new Meal(7, LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410));
        idCounter = new AtomicInteger(7);
    }

    private static class MealsStorageHolder {
        public static final MealArrayStorage INSTANCE = new MealArrayStorage();
    }

    public static MealArrayStorage getInstance() {
        return MealsStorageHolder.INSTANCE;
    }

    @Override
    public void deleteById(Integer id) {
        log.debug("deleteById {}", id);
        storage.remove(id);
    }

    @Override
    public Optional<Meal> findById(Integer id) {
        log.debug("findById {}", id);
        Meal mealFound = storage.get(id);
        return (mealFound == null) ? Optional.empty() : Optional.of(mealFound);
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
