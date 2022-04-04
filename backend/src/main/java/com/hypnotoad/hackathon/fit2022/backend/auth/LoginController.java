package com.hypnotoad.hackathon.fit2022.backend.auth;

import com.hypnotoad.hackathon.fit2022.backend.auth.password.PasswordHasher;
import com.hypnotoad.hackathon.fit2022.backend.auth.token.UserPrimitiveTokensRepository;
import com.hypnotoad.hackathon.fit2022.backend.responses.*;
import com.hypnotoad.hackathon.fit2022.backend.users.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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
    public ResponseEntity<Response> signUp(
            @RequestParam String username,
            @RequestParam String password
    ) {
        log.debug("signUp issued by user {}", username);

        var exists = userRepository.findByUsername(username);
        if (exists != null) {
            log.debug("Such user already exists");
            return ResponseEntity.status(401).body(new FailResponse("User already exists"));
        }

        var password_hash = passwordHasher.hash(password);
        var user = userRepository.createUser(username, password_hash);
        if (user == null)
            return ResponseEntity.status(500).body(new FailResponse("Couldn't create user"));
        log.debug("Created user: {}", user);

        var token = userPrimitiveTokensRepository.createToken(user);
        log.debug("Created token: {}", token);

        return ResponseEntity.status(200).body(new AuthResponse(token.getToken()));
    }

    @GetMapping("/api/signIn")
    public ResponseEntity<Response> signIn(
            @RequestParam String username,
            @RequestParam String password
    ) {
        log.debug("signIn issued by user {}", username);

        var user = userRepository.findByUsername(username);
        if (user == null) {
            log.debug("Such user doesn't exists");
            return ResponseEntity.status(401).body(new FailResponse("User doesn't exists"));
        }

        var password_hash = passwordHasher.hash(password);
        var valid = userRepository.validatePasswordHashByUsername(username, password_hash);
        if (!valid) {
            log.debug("User's credentials are invalid");
            return ResponseEntity.status(403).body(new FailResponse("Invalid credentials"));
        }

        userPrimitiveTokensRepository.deleteTokenByUser(user);
        var token = userPrimitiveTokensRepository.createToken(user);
        log.debug("Created token: {}", token);

        return ResponseEntity.status(200).body(new AuthResponse(token.getToken()));
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
        var deleted = userPrimitiveTokensRepository.deleteTokenByUser(user);
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

        return ResponseEntity.status(200).body(new GetMeResponse(user));
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

    public LoginController(
            UserRepository userRepository,
            PasswordHasher passwordHasher,
            UserPrimitiveTokensRepository userPrimitiveTokensRepository) {
        this.userPrimitiveTokensRepository = userPrimitiveTokensRepository;
        this.userRepository = userRepository;
        this.passwordHasher = passwordHasher;
    }
}
