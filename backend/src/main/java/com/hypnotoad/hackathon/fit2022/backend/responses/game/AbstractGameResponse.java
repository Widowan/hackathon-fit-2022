package com.hypnotoad.hackathon.fit2022.backend.responses.game;

import com.hypnotoad.hackathon.fit2022.backend.configurations.ResponseStyle;
import com.hypnotoad.hackathon.fit2022.backend.games.Game;
import com.hypnotoad.hackathon.fit2022.backend.responses.Response;
import org.immutables.value.Value;

@Value.Immutable
@ResponseStyle
abstract public class AbstractGameResponse extends Response {
    abstract public Game getGame();
}
