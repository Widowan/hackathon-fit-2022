package com.hypnotoad.hackathon.fit2022.backend.gameresults;

public class GameTotalResult {
    private int userId;
    private int gameId;
    private int sumScore;

    public GameTotalResult() {}

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public int getSumScore() {
        return sumScore;
    }

    public void setSumScore(int sumScore) {
        this.sumScore = sumScore;
    }
}
