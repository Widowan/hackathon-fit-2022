package com.hypnotoad.hackathon.fit2022.backend.games;

public class Game {
    private int id;
    private String name;
    private String description;
    private String rules;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getRules() {
        return rules;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRules(String rules) {
        this.rules = rules;
    }
}
