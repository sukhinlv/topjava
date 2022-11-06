package ru.javawebinar.topjava.service.datajpa;

import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.AbstractMealServiceTest;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.Assert.assertThrows;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

@ActiveProfiles(profiles = Profiles.DATAJPA)
public class DataJpaMealServiceTest extends AbstractMealServiceTest {

    @Test
    public void getByIdWithUser() {
        Meal actual = service.getByIdWithUser(ADMIN_MEAL_ID, ADMIN_ID);
        MEAL_MATCHER.assertMatch(actual, adminMeal1);
        assertThat(actual.getUser())
                .usingRecursiveComparison()
                .ignoringFields("password", "meals").isEqualTo(admin);
    }

    @Test
    public void getByIdWithUserNotFound() {
        assertThrows(NotFoundException.class, () -> service.getByIdWithUser(NOT_FOUND, USER_ID));
    }

    @Test
    public void getByIdWithUserNotOwn() {
        assertThrows(NotFoundException.class, () -> service.getByIdWithUser(ADMIN_MEAL_ID, USER_ID));
    }
}
