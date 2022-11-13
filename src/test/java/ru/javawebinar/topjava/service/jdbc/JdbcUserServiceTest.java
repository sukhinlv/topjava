package ru.javawebinar.topjava.service.jdbc;

import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.service.AbstractUserServiceTest;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertThrows;
import static ru.javawebinar.topjava.Profiles.JDBC;

@ActiveProfiles(JDBC)
public class JdbcUserServiceTest extends AbstractUserServiceTest {

    @Test
    public void saveNotValid() {
        assertThrows(IllegalArgumentException.class, () -> service.create(new User(null, null, "uzver@yandex.ru", "uzverPass", Role.USER)));
        assertThrows(IllegalArgumentException.class, () -> service.create(new User(null, " ", "uzver@yandex.ru", "uzverPass", Role.USER)));
        assertThrows(IllegalArgumentException.class, () -> service.create(new User(null, "u", "uzver@yandex.ru", "uzverPass", Role.USER)));
        assertThrows(IllegalArgumentException.class, () -> service.create(new User(null, "Test uzver".repeat(15), "uzver@yandex.ru", "uzverPass", Role.USER)));
        assertThrows(IllegalArgumentException.class, () -> service.create(new User(null, "Test uzver", "uzveryandex.ru", "uzverPass", Role.USER)));
        assertThrows(IllegalArgumentException.class, () -> service.create(new User(null, "Test uzver", null, "uzverPass", Role.USER)));
        assertThrows(IllegalArgumentException.class, () -> service.create(new User(null, "Test uzver", " ", "uzverPass", Role.USER)));
        assertThrows(IllegalArgumentException.class, () -> service.create(new User(null, "Test uzver", "uzver@yandex.ru".repeat(15), "uzverPass", Role.USER)));
        assertThrows(IllegalArgumentException.class, () -> service.create(new User(null, "Test uzver", "uzver@yandex.ru", null, Role.USER)));
        assertThrows(IllegalArgumentException.class, () -> service.create(new User(null, "Test uzver", "uzver@yandex.ru", " ", Role.USER)));
        assertThrows(IllegalArgumentException.class, () -> service.create(new User(null, "Test uzver", "uzver@yandex.ru", "Pass", Role.USER)));
        assertThrows(IllegalArgumentException.class, () -> service.create(new User(null, "Test uzver", "uzver@yandex.ru", "uzverPass".repeat(20), Role.USER)));
        assertThrows(IllegalArgumentException.class, () -> service.create(new User(null, "Test uzver", "uzver@yandex.ru", "uzverPass", 9, true, new Date(), List.of(Role.USER))));
        assertThrows(IllegalArgumentException.class, () -> service.create(new User(null, "Test uzver", "uzver@yandex.ru", "uzverPass", 20000, true, new Date(), List.of(Role.USER))));
    }

}
