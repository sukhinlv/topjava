package ru.javawebinar.topjava.service.datajpa;

import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.UserTestData;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.service.AbstractUserServiceTest;

import java.util.List;

import static ru.javawebinar.topjava.UserTestData.USER_ID;
import static ru.javawebinar.topjava.UserTestData.USER_WITH_MEALS_MATCHER;

@ActiveProfiles(profiles = Profiles.DATAJPA)
public class DataJpaUserServiceTest extends AbstractUserServiceTest {

    @Test
    public void getByIdWithMeals() {
        User user = service.getByIdWithMeals(USER_ID);
        User testUser = UserTestData.user;
        testUser.setMeals(List.of(
                MealTestData.meal7,
                MealTestData.meal6,
                MealTestData.meal5,
                MealTestData.meal4,
                MealTestData.meal3,
                MealTestData.meal2,
                MealTestData.meal1
        ));
        USER_WITH_MEALS_MATCHER.assertMatch(user, testUser);
    }
}
