package com.hypnotoad.hackathon.fit2022.backend.responses;

import com.hypnotoad.hackathon.fit2022.backend.configurations.ResponseStyle;
import org.immutables.value.Value;

@Value.Immutable
@ResponseStyle
abstract public class AbstractSuccessResponse extends Response {
    abstract public String getMessage();
}
