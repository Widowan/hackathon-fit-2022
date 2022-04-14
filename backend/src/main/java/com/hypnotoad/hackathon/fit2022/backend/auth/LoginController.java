package com.hypnotoad.hackathon.fit2022.backend.auth;

import com.hypnotoad.hackathon.fit2022.backend.auth.password.PasswordHasher;
import com.hypnotoad.hackathon.fit2022.backend.auth.token.UserPrimitiveTokensRepository;
import com.hypnotoad.hackathon.fit2022.backend.configurations.Strings;
import com.hypnotoad.hackathon.fit2022.backend.responses.FailResponse;
import com.hypnotoad.hackathon.fit2022.backend.responses.Response;
import com.hypnotoad.hackathon.fit2022.backend.responses.ResponseWrapper;
import com.hypnotoad.hackathon.fit2022.backend.responses.SuccessResponse;
import com.hypnotoad.hackathon.fit2022.backend.responses.auth.AuthResponse;
import com.hypnotoad.hackathon.fit2022.backend.responses.auth.GetMeResponse;
import com.hypnotoad.hackathon.fit2022.backend.users.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

// FIXME: Token checking into aspects
@RestController
public class LoginController {
    UserRepository userRepository;
    PasswordHasher passwordHasher;
    UserPrimitiveTokensRepository userPrimitiveTokensRepository;
    @Autowired Strings strings;
    static final Logger log = LoggerFactory.getLogger(LoginController.class);

    @GetMapping("/api/signUp")
    @PostMapping("/api/signUp")
    public ResponseEntity<Response> signUp(
            @RequestParam String username,
            @RequestParam String password
    ) {
        log.debug("signUp issued by user {}", username);

        if (username.isBlank() || password.isBlank()) {
            log.debug(strings.emptyFields);
            return ResponseWrapper.fail(401, strings.emptyFields);
        }

        // Check if already exists
        var user = userRepository.findByUsername(username)
                .peek(ig -> log.debug(strings.userExists));
        if (user.isDefined())
            return ResponseWrapper.fail(401, strings.userExists);

        // Create user if not
        var passwordHash = passwordHasher.hash(password);
        var createdUser = userRepository.createUser(username, passwordHash)
                .onEmpty(() -> log.debug(strings.cantCreateUser));
        if (createdUser.isEmpty())
            return ResponseWrapper.fail(500, strings.cantCreateToken);

        // Create token to give
        var token = userPrimitiveTokensRepository.createToken(createdUser.get())
                .onEmpty(() -> log.debug(strings.cantCreateToken));
        if (token.isEmpty())
            return ResponseWrapper.fail(500, strings.cantCreateToken);

        return ResponseEntity.status(200).body(new AuthResponse(token.get().getToken()));
    }

    @GetMapping("/api/signIn")
    @PostMapping("/api/signIn")
    public ResponseEntity<Response> signIn(
            @RequestParam String username,
            @RequestParam String password
    ) {
        log.debug("signIn issued by user {}", username);

        if (username.isBlank() || password.isBlank()) {
            log.debug(strings.emptyFields);
            return ResponseWrapper.fail(401, strings.emptyFields);
        }

        // Check if user even exists
        var user = userRepository.findByUsername(username)
                .onEmpty(() -> log.debug(strings.userNotExists));
        if (user.isEmpty())
            return ResponseWrapper.fail(401, strings.userNotExists);

        // Check if password is valid
        var password_hash = passwordHasher.hash(password);
        var valid = userRepository.validatePasswordHashByUsername(username, password_hash)
                .onEmpty(() -> log.debug(strings.passwordValidationFailed));
        if (valid.isEmpty())
            return ResponseWrapper.fail(500, strings.somethingWentWrong);
        else if (!valid.get())
            return ResponseWrapper.fail(403, strings.invalidCredentials);

        // Try to delete (invalidate) user's token
        var deleted = userPrimitiveTokensRepository.deleteTokenByUser(user.get())
                .onEmpty(() -> log.debug(strings.tokenNotDeleted));
        if (deleted.isEmpty() || !deleted.get())
            return ResponseEntity.status(500).body(new FailResponse(strings.somethingWentWrong));

        // Create token
        var token = userPrimitiveTokensRepository.createToken(user.get())
                .onEmpty(() -> log.debug(strings.cantCreateToken));
        if (token.isEmpty())
            return ResponseEntity.status(500).body(new FailResponse(strings.somethingWentWrong));

        return ResponseEntity.status(200).body(new AuthResponse(token.get().getToken()));
    }

    @GetMapping("/api/signOut")
    public ResponseEntity<Response> signOut(@RequestParam String token) {
        log.debug("signOut issued by user with token {}", token);

        // Check if token is valid
        var valid = userPrimitiveTokensRepository.validateTokenNoProlong(token)
                .onEmpty(() -> log.debug(strings.invalidToken));
        if (valid.isEmpty())   return ResponseWrapper.fail(500, strings.somethingWentWrong);
        else if (!valid.get()) return ResponseWrapper.fail(403, strings.invalidToken);

        // Process user
        var user = userRepository.findByToken(token)
                .onEmpty(() -> log.debug(strings.userNotExists));
        if (user.isEmpty()) return ResponseWrapper.fail(500, strings.userNotExists);

        // Invalidate token
        var deleted = userPrimitiveTokensRepository.deleteTokenByUser(user.get())
                .onEmpty(() -> log.debug(strings.tokenNotDeleted));
        if (deleted.isEmpty() || !deleted.get()) return ResponseWrapper.fail(500, strings.tokenNotDeleted);

        return ResponseEntity.status(200).body(new SuccessResponse("Success"));
    }

    @GetMapping("/api/getMe")
    public ResponseEntity<Response> getMe(@RequestParam String token) {
        log.debug("getMe issued by user with token {}", token);

        // Check if token is valid
        var valid = userPrimitiveTokensRepository.validateToken(token)
                .onEmpty(() -> log.debug(strings.invalidToken));
        if (valid.isEmpty())   return ResponseWrapper.fail(500, strings.somethingWentWrong);
        else if (!valid.get()) return ResponseWrapper.fail(403, strings.invalidToken);

        // Process user
        var user = userRepository.findByToken(token)
                .onEmpty(() -> log.debug(strings.userNotExists));
        if (user.isEmpty()) return ResponseWrapper.fail(500, strings.userNotExists);

        return ResponseEntity.status(200).body(new GetMeResponse(user.get()));
    }

    @GetMapping("/api/usernameIsUnique")
    public ResponseEntity<Response> usernameIsUnique(@RequestParam String username) {
        log.debug("usernameIsUnique issued by user {}", username);

        var unique = userRepository.findByUsername(username);
        if (unique.isDefined()) return ResponseWrapper.fail(401, strings.usernameNotUnique);

        return ResponseEntity.status(200).body(new SuccessResponse(strings.usernameUnique));
    }

    // SECURITY: This is basically waiting to be hacked even with sanitizing
    @GetMapping("/api/setAvatar")
    public ResponseEntity<Response> setAvatar(@RequestParam String token, @RequestParam String avatar) {
        log.debug("setAvatar issued with token {} and avatar {}", token, avatar);

        var valid = userPrimitiveTokensRepository.validateToken(token)
                .onEmpty(() -> log.debug(strings.invalidToken));
        if (valid.isEmpty()) return ResponseWrapper.fail(500, strings.somethingWentWrong);
        else if (!valid.get()) return ResponseWrapper.fail(403, strings.invalidToken);

        if (avatar.isBlank()) {
            log.debug(strings.emptyFields);
            return ResponseWrapper.fail(401, strings.emptyFields);
        }

        var user = userRepository.findByToken(token)
                .onEmpty(() -> log.debug(strings.userNotExists));
        if (user.isEmpty()) return ResponseWrapper.fail(500, strings.userNotExists);

        var success = userRepository.setAvatarByUserId(user.get().getId(), avatar)
                .onEmpty(() -> log.debug(strings.avatarSetFail));
        if (success.isEmpty() || !success.get()) return ResponseWrapper.fail(500, strings.avatarSetFail);

        return ResponseEntity.status(200).body(new SuccessResponse("Success"));
    }

    public LoginController(
            UserRepository userRepository,
            PasswordHasher passwordHasher,
            UserPrimitiveTokensRepository userPrimitiveTokensRepository
    ) {
        this.userPrimitiveTokensRepository = userPrimitiveTokensRepository;
        this.userRepository = userRepository;
        this.passwordHasher = passwordHasher;
    }
}
