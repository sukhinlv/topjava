package ru.javawebinar.topjava.repository.inmemory;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepository implements MealRepository {
    private final Map<Integer, Meal> repository = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.meals.forEach(meal -> this.save(meal, meal.getUserId()));
    }

    @Override
    public Meal save(Meal meal, int userId) {
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            meal.setUserId(userId);
            repository.put(meal.getId(), meal);
            return meal;
        }
        // handle case: update, but not present in storage
        Meal updatedMeal = repository.computeIfPresent(meal.getId(),
                (id, oldMeal) -> meal.getUserId() == userId && oldMeal.getUserId() == userId ? meal : oldMeal);
        return (updatedMeal != null && updatedMeal.getUserId() == userId) ? updatedMeal : null;
    }

    @Override
    public boolean delete(int id, int userId) {
        Meal meal = repository.get(id);
        return meal == null || meal.getUserId() != userId ? false : repository.remove(id) != null;
    }

    @Override
    public Meal get(int id, int userId) {
        Meal meal = repository.get(id);
        return (meal != null && meal.getUserId() != userId) ? null : meal;
    }

    @Override
    public List<Meal> getAll(int userId) {
        return getAllFiltered(userId, null, null);
    }

    @Override
    public List<Meal> getAllFiltered(int userId, LocalDate fromDate, LocalDate toDate) {
        return repository.values().stream()
                .filter(meal -> meal.getUserId() == userId)
                .filter(meal -> fromDate == null || meal.getDate().compareTo(fromDate) >= 0)
                .filter(meal -> toDate == null || meal.getDate().compareTo(toDate) <= 0)
                .sorted(Comparator.comparing(Meal::getDate)
                        .thenComparing(Meal::getTime)
                        .reversed())
                .collect(Collectors.toList());
    }
}
