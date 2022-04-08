package com.hypnotoad.hackathon.fit2022.backend.responses.gameresults;

import com.hypnotoad.hackathon.fit2022.backend.gameresults.GameTotalResult;
import com.hypnotoad.hackathon.fit2022.backend.responses.Response;

public class GameTotalResultResponse extends Response {
    private GameTotalResult gameTotalResult;

    public GameTotalResultResponse(GameTotalResult gameTotalResult) {
        this.ok = true;
        this.gameTotalResult = gameTotalResult;
    }

    public GameTotalResult getGameTotalResult() {
        return gameTotalResult;
    }

    public void setGameTotalResult(GameTotalResult gameTotalResult) {
        this.gameTotalResult = gameTotalResult;
    }
}
