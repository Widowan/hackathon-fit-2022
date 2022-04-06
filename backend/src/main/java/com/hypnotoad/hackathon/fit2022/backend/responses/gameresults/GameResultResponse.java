package com.hypnotoad.hackathon.fit2022.backend.responses.gameresults;

import com.hypnotoad.hackathon.fit2022.backend.gameresults.GameResult;
import com.hypnotoad.hackathon.fit2022.backend.responses.Response;

public class GameResultResponse extends Response {
    private GameResult gameResult;

    public GameResultResponse(GameResult gameResult) {
        this.ok = true;
        this.gameResult = gameResult;
    }

    public GameResult getGameResult() {
        return gameResult;
    }

    public void setGameResult(GameResult gameResult) {
        this.gameResult = gameResult;
    }
}
