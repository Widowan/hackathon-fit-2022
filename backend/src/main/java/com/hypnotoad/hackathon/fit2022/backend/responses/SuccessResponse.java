package com.hypnotoad.hackathon.fit2022.backend.responses;

public class SuccessResponse extends Response {
    private String message;

    public SuccessResponse(String message) {
        this.ok = true;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
