package ru.javawebinar.topjava.web.meal;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.Util;
import ru.javawebinar.topjava.util.exception.NotFoundException;
import ru.javawebinar.topjava.web.AbstractControllerTest;
import ru.javawebinar.topjava.web.SecurityUtil;
import ru.javawebinar.topjava.web.json.JsonUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.USER_ID;
import static ru.javawebinar.topjava.UserTestData.user;

class MealRestControllerTest extends AbstractControllerTest {

    private static final String REST_URL = MealRestController.REST_URL + '/';

    @Autowired
    private MealService mealService;

    @Test
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + MEAL1_ID))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MEAL_MATCHER.contentJson(meal1));
    }

    @Test
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL + MEAL1_ID))
                .andDo(print())
                .andExpect(status().isNoContent());

        assertThrows(NotFoundException.class, () -> mealService.get(MEAL1_ID, USER_ID));
    }

    @Test
    void getAll() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MEAL_TO_MATCHER.contentJson(MealsUtil.getTos(meals, SecurityUtil.authUserCaloriesPerDay())));
    }

    @Test
    void createWithLocation() throws Exception {
        Meal meal = getNew();

        MvcResult result = perform(MockMvcRequestBuilders.post(REST_URL, meal)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(meal)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn();

        Meal expected = JsonUtil.readValue(result.getResponse().getContentAsString(), Meal.class);
        meal.setId(expected.getId());

        MEAL_MATCHER.assertMatch(expected, meal);
        MEAL_MATCHER.assertMatch(mealService.get(meal.getId(), USER_ID), meal);
    }

    @Test
    void update() throws Exception {
        Meal meal = getUpdatedMeal();

        perform(MockMvcRequestBuilders.put(REST_URL + "{id}", MEAL1_ID)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(JsonUtil.writeValue(meal)))
                .andDo(print())
                .andExpect(status().isNoContent());

        MEAL_MATCHER.assertMatch(mealService.get(MEAL1_ID, USER_ID), meal);
    }

    @Test
    void getBetween() throws Exception {
        List<MealTo> mealTos = MealsUtil.getTos(meals, user.getCaloriesPerDay());

        List<MealTo> expectedMealTos = mealTos.stream()
                .filter(mealTo -> Util.isBetweenHalfOpen(
                        mealTo.getDateTime().toLocalDate(),
                        LocalDate.of(2020, 1, 31),
                        null))
                .toList();
        perform(MockMvcRequestBuilders.get(REST_URL + "filter")
                .param("startDate", "2020-01-31"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MEAL_TO_MATCHER.contentJson(expectedMealTos));

        expectedMealTos = mealTos.stream()
                .filter(mealTo -> Util.isBetweenHalfOpen(
                        mealTo.getDateTime().toLocalTime(),
                        LocalTime.of(10, 0),
                        null))
                .toList();
        perform(MockMvcRequestBuilders.get(REST_URL + "filter")
                .param("startTime", "10:00"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MEAL_TO_MATCHER.contentJson(expectedMealTos));

        expectedMealTos = mealTos.stream()
                .filter(mealTo -> Util.isBetweenHalfOpen(
                        mealTo.getDateTime().toLocalDate(),
                        LocalDate.of(2020, 1, 31),
                        LocalDate.of(2020, 1, 31).plusDays(1)))
                .filter(mealTo -> Util.isBetweenHalfOpen(
                        mealTo.getDateTime().toLocalTime(),
                        LocalTime.of(10, 0),
                        LocalTime.of(20, 0)))
                .toList();
        perform(MockMvcRequestBuilders.get(REST_URL + "filter")
                .param("startDate", "2020-01-31")
                .param("startTime", "10:00")
                .param("endDate", "2020-01-31")
                .param("endTime", "20:00"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MEAL_TO_MATCHER.contentJson(expectedMealTos));
    }
}
