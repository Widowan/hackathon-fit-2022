package com.hypnotoad.hackathon.fit2022.backend.auth.token;

import java.text.MessageFormat;

public class PrimitiveToken {
    private int userId;
    private String token;
    private long expiryTimestamp;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public long getExpiryTimestamp() {
        return expiryTimestamp;
    }

    public void setExpiryTimestamp(long expiryTimestamp) {
        this.expiryTimestamp = expiryTimestamp;
    }

    @Override
    public String toString() {
        return String.format(
                "PrimitiveToken[userId=%d,token=%s,expiryTimestamp=%s]",
                userId, token, expiryTimestamp);
    }
}
