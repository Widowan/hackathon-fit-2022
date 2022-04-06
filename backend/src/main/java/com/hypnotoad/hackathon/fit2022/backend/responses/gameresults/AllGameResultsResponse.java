package com.hypnotoad.hackathon.fit2022.backend.responses.gameresults;

import com.hypnotoad.hackathon.fit2022.backend.gameresults.GameResult;
import com.hypnotoad.hackathon.fit2022.backend.responses.Response;

import java.util.List;

public class AllGameResultsResponse extends Response {
    private List<GameResult> gameResults;

    public AllGameResultsResponse(List<GameResult> gameResults) {
        this.ok = true;
        this.gameResults = gameResults;
    }

    public List<GameResult> getGameResults() {
        return gameResults;
    }

    public void setGameResults(List<GameResult> gameResults) {
        this.gameResults = gameResults;
    }
}
