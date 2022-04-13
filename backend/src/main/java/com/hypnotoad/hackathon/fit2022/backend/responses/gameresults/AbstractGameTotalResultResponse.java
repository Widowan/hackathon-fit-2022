package com.hypnotoad.hackathon.fit2022.backend.responses.gameresults;

import com.hypnotoad.hackathon.fit2022.backend.configurations.ResponseStyle;
import com.hypnotoad.hackathon.fit2022.backend.gameresults.GameTotalResult;
import com.hypnotoad.hackathon.fit2022.backend.responses.Response;
import org.immutables.value.Value;

@Value.Immutable
@ResponseStyle
abstract public class AbstractGameTotalResultResponse extends Response {
    abstract public GameTotalResult getGameTotalResult();
}
