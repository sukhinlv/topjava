package ru.javawebinar.topjava.web.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;
import ru.javawebinar.topjava.to.UserTo;

import java.util.Objects;

// https://stackoverflow.com/questions/26501348/how-to-combine-jsr-303-and-spring-validator-class-in-a-service-layer/26733582#26733582
// https://habr.com/ru/post/424819/
@Component
public class UserRegisteredValidator implements org.springframework.validation.Validator {

    private final UserRepository userRepository;

    public UserRegisteredValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return UserTo.class.isAssignableFrom(clazz) || User.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        String email;
        Integer id;
        if (UserTo.class.isAssignableFrom(target.getClass())) {
            email = ((UserTo) target).getEmail();
            id = ((UserTo) target).getId();
        } else {
            email = ((User) target).getEmail();
            id = ((User) target).getId();
        }

        User userByEmail = userRepository.getByEmail(email);
        if (userByEmail != null && !Objects.equals(userByEmail.getId(), id)) {
            errors.rejectValue("email", "user.already.registered", "User with this email already exists");
        }
    }
}
