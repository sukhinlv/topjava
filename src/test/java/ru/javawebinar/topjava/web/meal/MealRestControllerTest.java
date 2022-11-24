package ru.javawebinar.topjava.web.meal;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.javawebinar.topjava.MatcherFactory;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.exception.NotFoundException;
import ru.javawebinar.topjava.web.AbstractControllerTest;
import ru.javawebinar.topjava.web.SecurityUtil;
import ru.javawebinar.topjava.web.json.JsonUtil;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

class MealRestControllerTest extends AbstractControllerTest {

    private static final String REST_URL = MealRestController.REST_URL + '/';

    @Autowired
    private MealService mealService;

    @Test
    void shouldGet() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + MEAL1_ID))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MEAL_MATCHER.contentJson(meal1));
    }

    @Test
    void shouldDelete() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL + MEAL1_ID))
                .andDo(print())
                .andExpect(status().isNoContent());

        assertThrows(NotFoundException.class, () -> mealService.get(MEAL1_ID, USER_ID));
    }

    @Test
    void shouldGetAll() throws Exception {
        MatcherFactory.Matcher<MealTo> mealToMatcher = MatcherFactory.usingIgnoringFieldsComparator(MealTo.class);

        perform(MockMvcRequestBuilders.get(REST_URL))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(mealToMatcher.contentJson(MealsUtil.getTos(meals, SecurityUtil.authUserCaloriesPerDay())));
    }

    @Test
    void shouldCreateWithLocation() throws Exception {
        MatcherFactory.Matcher<Meal> mealMatcher = MatcherFactory.usingIgnoringFieldsComparator(Meal.class, "id");
        Meal meal = new Meal(LocalDateTime.of(2022, 11, 15, 10, 30), "Some new meal", 500);

        perform(MockMvcRequestBuilders.post(REST_URL, meal)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(meal)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(mealMatcher.contentJson(meal));
    }

    @Test
    void shouldUpdate() throws Exception {
        Meal meal = mealService.get(MEAL1_ID, USER_ID);
        meal.setDateTime(LocalDateTime.of(2022, 11, 15, 10, 30));
        meal.setDescription("New description");
        meal.setCalories(13);

        perform(MockMvcRequestBuilders.put(REST_URL + "{id}", MEAL1_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(meal)))
                .andDo(print())
                .andExpect(status().isNoContent());

        assertThat(mealService.get(MEAL1_ID, USER_ID)).usingRecursiveComparison().isEqualTo(meal);
    }

    @Test
    void shouldGetBetween() throws Exception {
        MatcherFactory.Matcher<MealTo> mealToMatcher = MatcherFactory.usingIgnoringFieldsComparator(MealTo.class);

        perform(MockMvcRequestBuilders.get(REST_URL + "filter")
                .param("startDate", "2020-01-31")
                .param("startTime", "10:00")
                .param("endDate", "2020-01-31")
                .param("endTime", "20:00"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(mealToMatcher.contentJson(MealsUtil.getFilteredTos(
                        List.of(meal7, meal6,meal5, meal4),
                        SecurityUtil.authUserCaloriesPerDay(),
                        LocalTime.of(10, 0),
                        LocalTime.of(20, 0))));

        perform(MockMvcRequestBuilders.get(REST_URL + "filter")
                .param("startDate", "2020-01-31"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(mealToMatcher.contentJson(MealsUtil.getFilteredTos(
                        List.of(meal7, meal6,meal5, meal4),
                        SecurityUtil.authUserCaloriesPerDay(),
                        null,
                        null)));
    }
}