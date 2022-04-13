package com.hypnotoad.hackathon.fit2022.backend.gameresults;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.immutables.value.Value;

@JsonDeserialize(as = ImmutableGameResult.class)
@JsonSerialize(as = ImmutableGameResult.class)
@Value.Immutable
@Value.Style(stagedBuilder = true)
public abstract class GameResult {
    abstract int     getId();
    abstract int     getUserId();
    abstract int     getGameId();
    abstract boolean getResult();
    abstract int     getScore();
    abstract float   getTimeElapsed();
    abstract int     getDateTimestamp();
}
