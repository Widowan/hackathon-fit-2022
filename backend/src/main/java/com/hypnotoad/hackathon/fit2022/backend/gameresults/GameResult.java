package com.hypnotoad.hackathon.fit2022.backend.gameresults;

import com.hypnotoad.hackathon.fit2022.backend.configurations.JsonEntityStyle;
import org.immutables.value.Value;

@Value.Immutable
@JsonEntityStyle
public abstract class GameResult {
    abstract int     getId();
    abstract int     getUserId();
    abstract int     getGameId();
    abstract boolean getResult();
    abstract int     getScore();
    abstract float   getTimeElapsed();
    abstract int     getDateTimestamp();
}
