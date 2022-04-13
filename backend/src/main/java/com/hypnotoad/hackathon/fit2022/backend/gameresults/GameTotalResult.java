package com.hypnotoad.hackathon.fit2022.backend.gameresults;

import com.hypnotoad.hackathon.fit2022.backend.configurations.JsonEntityStyle;
import org.immutables.value.Value;

@Value.Immutable
@JsonEntityStyle
abstract public class GameTotalResult {
    abstract int getUserId();
    abstract int getGameId();
    abstract int getSumScore();
}
