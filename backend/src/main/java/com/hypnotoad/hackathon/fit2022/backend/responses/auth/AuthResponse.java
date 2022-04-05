package com.hypnotoad.hackathon.fit2022.backend.responses.auth;

import com.hypnotoad.hackathon.fit2022.backend.responses.Response;

public class AuthResponse extends Response {
    private String token;

    public AuthResponse(String token) {
        this.ok = true;
        this.token = token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}
