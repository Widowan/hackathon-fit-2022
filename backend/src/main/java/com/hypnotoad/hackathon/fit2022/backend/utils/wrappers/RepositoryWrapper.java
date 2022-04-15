package com.hypnotoad.hackathon.fit2022.backend.utils.wrappers;

import com.hypnotoad.hackathon.fit2022.backend.auth.token.PrimitiveToken;
import com.hypnotoad.hackathon.fit2022.backend.auth.token.UserPrimitiveTokensRepository;
import com.hypnotoad.hackathon.fit2022.backend.configurations.Strings;
import com.hypnotoad.hackathon.fit2022.backend.responses.Response;
import com.hypnotoad.hackathon.fit2022.backend.users.User;
import com.hypnotoad.hackathon.fit2022.backend.users.UserRepository;
import io.vavr.control.Either;
import io.vavr.control.Option;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class RepositoryWrapper {
    final UserRepository userRepository;
    final UserPrimitiveTokensRepository userPrimitiveTokensRepository;
    @Autowired Strings strings;
    static final Logger log = LoggerFactory.getLogger(RepositoryWrapper.class);

    public Either<ResponseEntity<Response>, User> createUser(String username, String passwordHash) {
        return userRepository.createUser(username, passwordHash)
                .onEmpty(() -> log.error(strings.cantCreateUser))
                .toEither(ResponseWrapper.fail(500, strings.cantCreateUser));
    }

    public Either<ResponseEntity<Response>, ? extends PrimitiveToken> createToken(User user) {
        return userPrimitiveTokensRepository.createToken(user)
                .onEmpty(() -> log.error(strings.cantCreateToken))
                .toEither(ResponseWrapper.fail(500, strings.cantCreateToken));
    }

    public Either<ResponseEntity<Response>, User> userByUsername(String username, boolean shouldGet) {
        var user = userRepository.findByUsername(username);

        if (shouldGet) {
            if (user.isEmpty()) {
                log.debug(strings.userNotExists);
                return Either.left(ResponseWrapper.fail(401, strings.userNotExists));
            }
        } else if (user.isDefined()) {
            log.debug(strings.userExists);
            return Either.left(ResponseWrapper.fail(401, strings.userExists));
        }

        return Either.right(user.get());
    }

    public Either<ResponseEntity<Response>, User> userByToken(String token, boolean prolong) {
        var valid = prolong
                ? userPrimitiveTokensRepository.validateToken(token)
                : userPrimitiveTokensRepository.validateTokenNoProlong(token);

        if (valid.isEmpty()) {
            log.error(strings.somethingWentWrong);
            return Either.left(ResponseWrapper.fail(500, strings.somethingWentWrong));
        }
        else if (!valid.get()) {
            log.debug(strings.invalidToken);
            return Either.left(ResponseWrapper.fail(403, strings.invalidToken));
        }

        return userRepository.findByToken(token)
                .onEmpty(() -> log.error(strings.somethingWentWrong))
                .toEither(ResponseWrapper.fail(403, strings.invalidToken));
    }

    public Either<ResponseEntity<Response>, Boolean> deleteToken(User user) {
        return userPrimitiveTokensRepository.deleteTokenByUser(user)
                .onEmpty(() -> log.debug(strings.tokenNotDeleted))
                // We don't care about whether token was ACTUALLY deleted, just that
                // operation didn't fail, so we ignore the bool value inside of Option
                .toEither(ResponseWrapper.fail(500,strings.somethingWentWrong));
    }

    public Option<ResponseEntity<Response>> setAvatar(User user, String avatar) {
        var isSet = userRepository.setAvatarByUserId(user.getId(), avatar);

        if (isSet.isEmpty()) {
            log.debug(strings.somethingWentWrong);
            return Option.of(ResponseWrapper.fail(500, strings.somethingWentWrong));
        }
        else if (!isSet.get()) {
            log.debug(strings.avatarSetFail);
            return Option.of(ResponseWrapper.fail(401, strings.avatarSetFail));
        }

        return Option.none();
    }

    public RepositoryWrapper(UserRepository userRepository, UserPrimitiveTokensRepository userPrimitiveTokensRepository) {
        this.userRepository = userRepository;
        this.userPrimitiveTokensRepository = userPrimitiveTokensRepository;
    }
}
