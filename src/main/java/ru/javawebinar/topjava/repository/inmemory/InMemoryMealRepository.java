package ru.javawebinar.topjava.repository.inmemory;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepository implements MealRepository {
    private final Map<Integer, Map<Integer, Meal>> repository = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.meals.forEach(meal -> this.save(meal, meal.getUserId()));
    }

    @Override
    public Meal save(Meal meal, int userId) {
        AtomicReference<Meal> resultMeal = new AtomicReference<>(null);
        repository.compute(userId, (unused, userMeals) -> {
            if (userMeals == null) {
                userMeals = new HashMap<>();
            }
            if (meal.isNew()) {
                meal.setId(counter.incrementAndGet());
                meal.setUserId(userId);
                userMeals.put(meal.getId(), meal);
                resultMeal.set(meal);
            } else if (meal.getUserId() == userId) {
                // handle case: update, but not present in storage
                Meal updatedMeal = userMeals.computeIfPresent(meal.getId(),
                        (id, oldMeal) -> oldMeal.getUserId() == userId ? meal : oldMeal);
                resultMeal.set(updatedMeal != null && updatedMeal.getUserId() == userId ? updatedMeal : null);
            }
            return userMeals;
        });
        return resultMeal.get();
    }

    @Override
    public boolean delete(int id, int userId) {
        AtomicBoolean result = new AtomicBoolean(false);
        repository.computeIfPresent(userId, (unused, userMeals) -> {
            result.set(userMeals.remove(id) != null);
            return userMeals.size() == 0 ? null : userMeals;
        });
        return result.get();
    }

    @Override
    public Meal get(int id, int userId) {
        AtomicReference<Meal> resultMeal = new AtomicReference<>(null);
        repository.computeIfPresent(userId, (unused, userMeals) -> {
            Meal meal = userMeals.get(id);
            resultMeal.set(meal != null && meal.getUserId() != userId ? null : meal);
            return userMeals;
        });
        return resultMeal.get();
    }

    @Override
    public List<Meal> getAll(int userId) {
        return getAllFiltered(userId, null, null);
    }

    @Override
    public List<Meal> getAllFiltered(int userId, LocalDate fromDate, LocalDate toDate) {
        AtomicReference<List<Meal>> resultMeal = new AtomicReference<>(null);
        repository.computeIfPresent(userId, (unused, userMeals) -> {
            resultMeal.set(userMeals.values().stream()
                    .filter(meal -> meal.getUserId() == userId)
                    .filter(meal -> fromDate == null || meal.getDate().compareTo(fromDate) >= 0)
                    .filter(meal -> toDate == null || meal.getDate().compareTo(toDate) <= 0)
                    .sorted(Comparator.comparing(Meal::getDate)
                            .thenComparing(Meal::getTime)
                            .reversed())
                    .collect(Collectors.toList()));
            return userMeals;
        });
        return resultMeal.get();
    }
}
