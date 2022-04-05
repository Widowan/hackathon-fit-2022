package com.hypnotoad.hackathon.fit2022.backend.responses.game;

import com.hypnotoad.hackathon.fit2022.backend.games.Game;
import com.hypnotoad.hackathon.fit2022.backend.responses.Response;

public class GameResponse extends Response {
    private Game game;

    public GameResponse(Game game) {
        this.ok = true;
        this.game = game;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }
}
