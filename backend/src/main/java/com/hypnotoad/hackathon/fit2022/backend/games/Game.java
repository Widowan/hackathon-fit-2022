package com.hypnotoad.hackathon.fit2022.backend.games;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.immutables.value.Value;

@JsonDeserialize(as = ImmutableGame.class)
@JsonSerialize(as = ImmutableGame.class)
@Value.Immutable
@Value.Style(stagedBuilder = true)
public abstract class Game {
    abstract int    getId();
    abstract String getName();
    abstract String getDescription();
    abstract String getRules();
    abstract String getIcon();
}
