package com.hypnotoad.hackathon.fit2022.backend.responses.game;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.hypnotoad.hackathon.fit2022.backend.configurations.ResponseStyle;
import com.hypnotoad.hackathon.fit2022.backend.games.Game;
import com.hypnotoad.hackathon.fit2022.backend.responses.Response;
import org.immutables.value.Value;

import java.util.List;

@Value.Immutable
@ResponseStyle
abstract public class AbstractAllGamesResponse extends Response {
    abstract List<Game> getGames();
}
