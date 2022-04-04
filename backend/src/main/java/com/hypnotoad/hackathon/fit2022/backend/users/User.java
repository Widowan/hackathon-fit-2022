package com.hypnotoad.hackathon.fit2022.backend.users;

public class User {
    private int id;
    private String username;

    public User() {}

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return String.format("User[id=%s,username=%s]", getId(), getUsername());
    }
}
