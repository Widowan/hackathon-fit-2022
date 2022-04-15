package com.hypnotoad.hackathon.fit2022.backend.auth;

import com.hypnotoad.hackathon.fit2022.backend.auth.password.PasswordHasher;
import com.hypnotoad.hackathon.fit2022.backend.auth.token.UserPrimitiveTokensRepository;
import com.hypnotoad.hackathon.fit2022.backend.configurations.Strings;
import com.hypnotoad.hackathon.fit2022.backend.responses.Response;
import com.hypnotoad.hackathon.fit2022.backend.responses.SuccessResponse;
import com.hypnotoad.hackathon.fit2022.backend.responses.auth.AuthResponse;
import com.hypnotoad.hackathon.fit2022.backend.responses.auth.GetMeResponse;
import com.hypnotoad.hackathon.fit2022.backend.users.UserRepository;
import com.hypnotoad.hackathon.fit2022.backend.utils.Validator;
import com.hypnotoad.hackathon.fit2022.backend.utils.wrappers.RepositoryWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class LoginController {
    final UserRepository userRepository;
    final PasswordHasher passwordHasher;
    final UserPrimitiveTokensRepository userPrimitiveTokensRepository;

    final Validator validator;
    final RepositoryWrapper repositoryWrapper;

    @Autowired Strings strings;
    static final Logger log = LoggerFactory.getLogger(LoginController.class);

    @GetMapping("/api/signUp")
    @PostMapping("/api/signUp")
    public ResponseEntity<Response> signUp(
            @RequestParam String username,
            @RequestParam String password
    ) {
        log.debug("signUp issued by user {}", username);

        var paramsInvalid = validator.params(username, password);
        if (paramsInvalid.isDefined()) return paramsInvalid.get();

        var usernameInvalid = repositoryWrapper.userByUsername(username, false);
        if (usernameInvalid.isLeft()) return usernameInvalid.getLeft();

        var passwordHash = passwordHasher.hash(password);
        var createdUser = repositoryWrapper.createUser(username, passwordHash);
        if (createdUser.isEmpty()) return createdUser.getLeft();

        var createdToken = repositoryWrapper.createToken(createdUser.get());
        if (createdToken.isEmpty()) return createdToken.getLeft();

        return ResponseEntity.status(200).body(new AuthResponse(createdToken.get().getToken()));
    }

    @GetMapping("/api/signIn")
    @PostMapping("/api/signIn")
    public ResponseEntity<Response> signIn(
            @RequestParam String username,
            @RequestParam String password
    ) {
        log.debug("signIn issued by user {}", username);

        var paramsInvalid = validator.params(username, password);
        if (paramsInvalid.isDefined()) return paramsInvalid.get();

        var user = repositoryWrapper.userByUsername(username, true);
        if (user.isLeft()) return user.getLeft();

        var passwordHash = passwordHasher.hash(password);
        var passwordInvalid = validator.passwordHash(username, passwordHash);
        if (passwordInvalid.isDefined()) return passwordInvalid.get();

        var deletedToken = repositoryWrapper.deleteToken(user.get());
        if (deletedToken.isLeft()) return deletedToken.getLeft();

        var createdToken = repositoryWrapper.createToken(user.get());
        if (createdToken.isLeft()) return createdToken.getLeft();

        return ResponseEntity.status(200).body(new AuthResponse(createdToken.get().getToken()));
    }

    @GetMapping("/api/signOut")
    public ResponseEntity<Response> signOut(@RequestParam String token) {
        log.debug("signOut issued by user with token {}", token);

        var user = repositoryWrapper.userByToken(token, false);
        if (user.isEmpty()) return user.getLeft();

        var deletedToken = repositoryWrapper.deleteToken(user.get());
        if (deletedToken.isLeft()) return deletedToken.getLeft();

        return ResponseEntity.status(200).body(new SuccessResponse("Success"));
    }

    @GetMapping("/api/getMe")
    public ResponseEntity<Response> getMe(@RequestParam String token) {
        log.debug("getMe issued by user with token {}", token);

        var user = repositoryWrapper.userByToken(token, true);
        if (user.isEmpty()) return user.getLeft();

        return ResponseEntity.status(200).body(new GetMeResponse(user.get()));
    }

    @GetMapping("/api/usernameIsUnique")
    public ResponseEntity<Response> usernameIsUnique(@RequestParam String username) {
        log.debug("usernameIsUnique issued by user {}", username);

        var unique = repositoryWrapper.userByUsername(username, false);
        if (unique.isEmpty()) return unique.getLeft();

        return ResponseEntity.status(200).body(new SuccessResponse(strings.usernameUnique));
    }

    // SECURITY: This is basically waiting to be hacked even with sanitizing
    @GetMapping("/api/setAvatar")
    public ResponseEntity<Response> setAvatar(@RequestParam String token, @RequestParam String avatar) {
        log.debug("setAvatar issued with token {} and avatar {}", token, avatar);

        var paramsInvalid = validator.params(avatar);
        if (paramsInvalid.isDefined()) return paramsInvalid.get();

        var user = repositoryWrapper.userByToken(token, true);
        if (user.isEmpty()) return user.getLeft();

        var setFail = repositoryWrapper.setAvatar(user.get(), avatar);
        if (setFail.isDefined()) return setFail.get();

        return ResponseEntity.status(200).body(new SuccessResponse("Success"));
    }

    public LoginController(
            UserRepository userRepository,
            PasswordHasher passwordHasher,
            UserPrimitiveTokensRepository userPrimitiveTokensRepository,
            Validator validator, RepositoryWrapper repositoryWrapper) {
        this.userPrimitiveTokensRepository = userPrimitiveTokensRepository;
        this.userRepository = userRepository;
        this.passwordHasher = passwordHasher;
        this.validator = validator;
        this.repositoryWrapper = repositoryWrapper;
    }
}
