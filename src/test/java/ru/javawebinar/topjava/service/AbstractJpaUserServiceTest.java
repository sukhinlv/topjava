package ru.javawebinar.topjava.service;

import org.springframework.beans.factory.annotation.Autowired;
import ru.javawebinar.topjava.repository.JpaUtil;

import java.util.Arrays;

import static ru.javawebinar.topjava.Profiles.NO_CACHE;

public abstract class AbstractJpaUserServiceTest extends AbstractUserServiceTest {

    @Autowired
    protected JpaUtil jpaUtil;

    @Override
    public void setup() {
        super.setup();
        if (!Arrays.asList(env.getActiveProfiles()).contains(NO_CACHE)) {
            jpaUtil.clear2ndLevelHibernateCache();
        }
    }
}
