package com.hypnotoad.hackathon.fit2022.backend.responses.game;

import com.hypnotoad.hackathon.fit2022.backend.games.Game;
import com.hypnotoad.hackathon.fit2022.backend.responses.Response;

import java.util.List;

public class AllGamesResponse extends Response {
    private List<Game> games;

    public AllGamesResponse(List<Game> games) {
        this.ok = true;
        this.games = games;
    }

    public List<Game> getGames() {
        return games;
    }

    public void setGames(List<Game> games) {
        this.games = games;
    }
}
