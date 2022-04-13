package com.hypnotoad.hackathon.fit2022.backend.gameresults;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.immutables.value.Value;

@JsonDeserialize(as = ImmutableLeaderboardRow.class)
@JsonSerialize(as = ImmutableLeaderboardRow.class)
@Value.Immutable
@Value.Style(stagedBuilder = true)
abstract public class LeaderboardRow {
    abstract int    getUserId();
    abstract int    getPlace();
    abstract int    sumScore();
    abstract String getUsername();
}
