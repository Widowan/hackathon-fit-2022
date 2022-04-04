package com.hypnotoad.hackathon.fit2022.backend.responses;

public abstract class Response {
    protected boolean ok;

    public boolean isOk() {
        return ok;
    }

    public void setOk(boolean ok) {
        this.ok = ok;
    }
}
