package com.hypnotoad.hackathon.fit2022.backend.responses.gameresults;

import com.hypnotoad.hackathon.fit2022.backend.gameresults.LeaderboardRow;
import com.hypnotoad.hackathon.fit2022.backend.responses.Response;

import java.util.List;

public class LeaderboardResponse extends Response {
    private List<LeaderboardRow> leaderboard;

    public LeaderboardResponse(List<LeaderboardRow> leaderboard) {
        this.ok = true;
        this.leaderboard = leaderboard;
    }

    public void setLeaderboard(List<LeaderboardRow> leaderboard) {
        this.leaderboard = leaderboard;
    }

    public List<LeaderboardRow> getLeaderboard() {
        return leaderboard;
    }
}
