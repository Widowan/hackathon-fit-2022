package com.hypnotoad.hackathon.fit2022.backend.utils;

import com.hypnotoad.hackathon.fit2022.backend.configurations.Strings;
import com.hypnotoad.hackathon.fit2022.backend.responses.Response;
import com.hypnotoad.hackathon.fit2022.backend.users.UserRepository;
import com.hypnotoad.hackathon.fit2022.backend.utils.wrappers.ResponseWrapper;
import io.vavr.control.Option;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class Validator {
    final UserRepository userRepository;
    @Autowired Strings strings;
    static final Logger log = LoggerFactory.getLogger(Validator.class);

    public Option<ResponseEntity<Response>> params(Object... args) {
        for (Object arg : args)
            if (arg == null || arg instanceof String s && s.isBlank()) {
                log.debug(strings.emptyFields);
                return Option.of(ResponseWrapper.fail(401, strings.emptyFields));
            }

        return Option.none();
    }

    public Option<ResponseEntity<Response>> passwordHash(String username, String passwordHash) {
        var valid = userRepository.validatePasswordHashByUsername(username, passwordHash)
                .onEmpty(() -> log.debug(strings.passwordValidationFailed));
        if (valid.isEmpty())
            return Option.of(ResponseWrapper.fail(500, strings.somethingWentWrong));
        else if (!valid.get())
            return Option.of(ResponseWrapper.fail(403, strings.invalidCredentials));

        return Option.none();
    }

    public Validator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
}
