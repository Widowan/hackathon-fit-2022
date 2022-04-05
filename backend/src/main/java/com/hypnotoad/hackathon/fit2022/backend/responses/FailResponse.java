package com.hypnotoad.hackathon.fit2022.backend.responses;

public class FailResponse extends Response {
    private String reason;

    public FailResponse(String reason) {
        this.ok = false;
        this.reason = reason;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
