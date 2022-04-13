package com.hypnotoad.hackathon.fit2022.backend.responses.gameresults;

import com.hypnotoad.hackathon.fit2022.backend.configurations.ResponseStyle;
import com.hypnotoad.hackathon.fit2022.backend.gameresults.LeaderboardRow;
import com.hypnotoad.hackathon.fit2022.backend.responses.Response;
import org.immutables.value.Value;

import java.util.List;

@Value.Immutable
@ResponseStyle
abstract public class AbstractLeaderboardResponse extends Response {
    abstract List<LeaderboardRow> getLeaderboard();
}
