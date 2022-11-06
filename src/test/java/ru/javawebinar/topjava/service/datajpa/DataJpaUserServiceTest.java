package ru.javawebinar.topjava.service.datajpa;

import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.UserTestData;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.service.AbstractUserServiceTest;

import static ru.javawebinar.topjava.UserTestData.*;

@ActiveProfiles(profiles = Profiles.DATAJPA)
public class DataJpaUserServiceTest extends AbstractUserServiceTest {

    @Test
    public void getByIdWithMeals() {
        User user = service.getByIdWithMeals(USER_ID);
        User testUser = UserTestData.user;
        USER_WITH_MEALS_MATCHER.assertMatch(user, testUser);

        User guest = service.getByIdWithMeals(GUEST_ID);
        USER_WITH_MEALS_MATCHER.assertMatch(guest, UserTestData.guest);
    }
}
