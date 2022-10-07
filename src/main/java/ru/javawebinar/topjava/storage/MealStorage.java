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

public class MealStorage implements Storage<Meal, Integer> {
    private final Map<Integer, Meal> storage = new ConcurrentHashMap<>();
    private final AtomicInteger idCounter;
    private static final Logger log = getLogger(MealStorage.class);

    private MealStorage() {
        // TODO remove hardcoded storage initialization
        idCounter = new AtomicInteger(0);
        storage.put(idCounter.incrementAndGet(), new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500));
        storage.put(idCounter.incrementAndGet(), new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000));
        storage.put(idCounter.incrementAndGet(), new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500));
        storage.put(idCounter.incrementAndGet(), new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100));
        storage.put(idCounter.incrementAndGet(), new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000));
        storage.put(idCounter.incrementAndGet(), new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500));
        storage.put(idCounter.incrementAndGet(), new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410));
    }

    private static class MealsStorageHolder {
        public static final MealStorage INSTANCE = new MealStorage();
    }

    public static MealStorage getInstance() {
        return MealsStorageHolder.INSTANCE;
    }

    @Override
    public void deleteAll() {
        // TODO: implement deleteAll()
        log.debug("deleteAll");
        storage.clear();
    }

    @Override
    public void deleteById(Integer id) {
        // TODO: implement deleteById()
        log.debug("deleteNyId {}", id);
        storage.remove(id);
    }

    @Override
    public Optional<Meal> findById(Integer id) {
        // TODO: implement findById()
        log.debug("findById {}", id);
        Meal mealFound = storage.get(id);
        return (mealFound == null) ? Optional.empty() : Optional.of(mealFound);
    }

    @Override
    public List<Meal> findAll() {
        // TODO: implement findAll()
        log.debug("findAll");
        return new ArrayList<>(storage.values());
    }

    @Override
    public void save(Meal entity) {
        // TODO: implement save()
        Integer newId = idCounter.incrementAndGet();
        Meal newMeal = new Meal(idCounter.get(), entity.getDateTime(), entity.getDescription(), entity.getCalories());
        log.debug("save meal {}", newMeal);
        storage.put(newId, newMeal);
    }
}
