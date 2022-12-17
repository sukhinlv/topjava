package ru.javawebinar.topjava.web.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.beanvalidation.SpringValidatorAdapter;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;
import ru.javawebinar.topjava.to.UserTo;

import java.util.Objects;

// https://stackoverflow.com/questions/26501348/how-to-combine-jsr-303-and-spring-validator-class-in-a-service-layer/26733582#26733582
// https://habr.com/ru/post/424819/
@Component
public class UserToValidator implements org.springframework.validation.Validator {

    private final UserRepository userRepository;
    private final SpringValidatorAdapter validator;

    public UserToValidator(UserRepository userRepository, SpringValidatorAdapter validator) {
        this.userRepository = userRepository;
        this.validator = validator;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return UserTo.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        // jsr303
        validator.validate(target, errors);

        // custom rules
        UserTo userTo = (UserTo) target;
        User userByEmail = userRepository.getByEmail(userTo.getEmail());
        if (userByEmail != null && !Objects.equals(userByEmail.getId(), userTo.getId())) {
            errors.rejectValue("email", "user.already.registered", "User with this email already exists");
        }
    }
}
