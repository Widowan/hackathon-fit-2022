package com.hypnotoad.hackathon.fit2022.backend.games;

import com.hypnotoad.hackathon.fit2022.backend.configurations.JsonEntityStyle;
import org.immutables.value.Value;

@Value.Immutable
@JsonEntityStyle
public abstract class Game {
    abstract int    getId();
    abstract String getName();
    abstract String getDescription();
    abstract String getRules();
    abstract String getIcon();
}
