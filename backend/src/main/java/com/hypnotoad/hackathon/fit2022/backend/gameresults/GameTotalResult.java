package com.hypnotoad.hackathon.fit2022.backend.gameresults;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.immutables.value.Value;

@JsonDeserialize(as = ImmutableGameTotalResult.class)
@JsonSerialize(as = ImmutableGameTotalResult.class)
@Value.Immutable
@Value.Style(stagedBuilder = true)
abstract public class GameTotalResult {
    abstract int getUserId();
    abstract int getGameId();
    abstract int getSumScore();
}
//public class GameTotalResult {
//    private int userId;
//    private int gameId;
//    private int sumScore;
//
//    public GameTotalResult() {}
//
//    public int getUserId() {
//        return userId;
//    }
//
//    public void setUserId(int userId) {
//        this.userId = userId;
//    }
//
//    public int getGameId() {
//        return gameId;
//    }
//
//    public void setGameId(int gameId) {
//        this.gameId = gameId;
//    }
//
//    public int getSumScore() {
//        return sumScore;
//    }
//
//    public void setSumScore(int sumScore) {
//        this.sumScore = sumScore;
//    }
//}
