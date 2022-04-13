package com.hypnotoad.hackathon.fit2022.backend.responses.gameresults;

import com.hypnotoad.hackathon.fit2022.backend.configurations.ResponseStyle;
import com.hypnotoad.hackathon.fit2022.backend.gameresults.GameResult;
import com.hypnotoad.hackathon.fit2022.backend.responses.Response;
import org.immutables.value.Value;

@Value.Immutable
@ResponseStyle
abstract public class AbstractGameResultResponse extends Response {
    abstract GameResult getGameResult();
}
