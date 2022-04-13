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
