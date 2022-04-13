package com.hypnotoad.hackathon.fit2022.backend.auth;

import com.hypnotoad.hackathon.fit2022.backend.auth.password.PasswordHasher;
import com.hypnotoad.hackathon.fit2022.backend.auth.token.UserPrimitiveTokensRepository;
import com.hypnotoad.hackathon.fit2022.backend.responses.FailResponse;
import com.hypnotoad.hackathon.fit2022.backend.responses.Response;
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
    static final Logger log = LoggerFactory.getLogger(LoginController.class);


    @GetMapping("/api/signUp")
    @PostMapping("/api/signUp")
    public ResponseEntity<? extends Response> signUp(
            @RequestParam String username,
            @RequestParam String password
    ) {
        log.debug("signUp issued by user {}", username);

        // TODO: Move everything to strings in resources

        if (username.isBlank() || password.isBlank()) {
            log.debug("Empty fields are not allowed");
            return ResponseEntity.status(401).body(new FailResponse("Empty fields"));
        }

        var user = userRepository.findByUsername(username)
                .peek(ig -> log.debug("Such user already exists"));
        if (user.isDefined())
            return ResponseEntity.status(401).body(new FailResponse("User already exists"));

        var passwordHash = passwordHasher.hash(password);
        var createdUser = userRepository.createUser(username, passwordHash)
                .onEmpty(() -> log.error("Couldn't create user"));
        if (createdUser.isEmpty())
            return ResponseEntity.status(500).body(new FailResponse("Couldn't create user"));

        var token = userPrimitiveTokensRepository.createToken(user.get())
                .onEmpty(() -> log.error("Couldn't create token"));
        if (token.isEmpty())
            return ResponseEntity.status(500).body(new FailResponse("Couldn't create token"));

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
            log.debug("Empty fields are not allowed");
            return ResponseEntity.status(401).body(new FailResponse("Empty fields"));
        }

        var user = userRepository.findByUsername(username)
                .onEmpty(() -> log.debug("User doesn't exists"));
        if (user.isEmpty())
            return ResponseEntity.status(401).body(new FailResponse("User doesn't exists"));

        var password_hash = passwordHasher.hash(password);
        var valid = userRepository.validatePasswordHashByUsername(username, password_hash);
        if (!valid) {
            log.debug("User's credentials are invalid");
            return ResponseEntity.status(403).body(new FailResponse("Invalid credentials"));
        }

        userPrimitiveTokensRepository.deleteTokenByUser(user.get());
        var token = userPrimitiveTokensRepository.createToken(user.get())
                .onEmpty(() -> log.error("Couldn't create token"));

        // TODO: Handle
        return ResponseEntity.status(200).body(new AuthResponse(token.get().getToken()));
    }

    @GetMapping("/api/signOut")
    public ResponseEntity<Response> signOut(@RequestParam String token) {
        log.debug("signOut issued by user with token {}", token);

        var valid = userPrimitiveTokensRepository.validateTokenNoProlong(token);
        if (!valid) {
            log.debug("Provided token is invalid");
            return ResponseEntity.status(403).body(new FailResponse("Invalid token"));
        }

        var user = userRepository.findByToken(token);
        var deleted = userPrimitiveTokensRepository.deleteTokenByUser(user.get());
        if (!deleted) {
            log.debug("Failed to expire token");
            return ResponseEntity.status(500).body(new FailResponse("Couldn't expire token"));
        }

        return ResponseEntity.status(200).body(new SuccessResponse("Success"));
    }

    @GetMapping("/api/getMe")
    public ResponseEntity<Response> getMe(@RequestParam String token) {
        log.debug("getMe issued by user with token {}", token);

        var valid = userPrimitiveTokensRepository.validateToken(token);
        if (!valid) {
            log.debug("Provided token is invalid");
            return ResponseEntity.status(403).body(new FailResponse("Invalid token"));
        }

        var user = userRepository.findByToken(token);

        return ResponseEntity.status(200).body(new GetMeResponse(user.get()));
    }

    @GetMapping("/api/usernameIsUnique")
    public ResponseEntity<Response> usernameIsUnique(@RequestParam String username) {
        log.debug("usernameIsUnique issued by user {}", username);

        var unique = userRepository.findByUsername(username);
        if (unique != null) {
            log.debug("Username is not unique");
            return ResponseEntity.status(401).body(new FailResponse("Username is not unique"));
        }

        return ResponseEntity.status(200).body(new SuccessResponse("Username is unique"));
    }

    // SECURITY: This is basically waiting to be hacked even with sanitizing
    @GetMapping("/api/setAvatar")
    public ResponseEntity<Response> setAvatar(@RequestParam String token, @RequestParam String avatar) {
        log.debug("setAvatar issued with token {} and avatar {}", token, avatar);

        var valid = userPrimitiveTokensRepository.validateToken(token);
        if (!valid) {
            log.debug("Provided token is invalid");
            return ResponseEntity.status(403).body(new FailResponse("Invalid token"));
        }

        if (avatar.isBlank()) {
            log.debug("Empty fields are not allowed");
            return ResponseEntity.status(401).body(new FailResponse("Empty fields"));
        }

        var user = userRepository.findByToken(token);
        var success = userRepository.setAvatarByUserId(user.get().getId(), avatar);

        if (!success) {
            return ResponseEntity.status(500).body(new FailResponse("Something went wrong"));
        }

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
