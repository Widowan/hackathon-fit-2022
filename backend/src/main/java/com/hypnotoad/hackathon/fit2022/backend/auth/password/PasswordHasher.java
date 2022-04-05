package com.hypnotoad.hackathon.fit2022.backend.auth.password;

public interface PasswordHasher {
    String hash(String password);
}
