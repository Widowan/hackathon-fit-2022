package com.hypnotoad.hackathon.fit2022.backend.responses;

import com.hypnotoad.hackathon.fit2022.backend.configurations.ResponseStyle;
import com.hypnotoad.hackathon.fit2022.backend.responses.Response;
import org.immutables.value.Value;

@Value.Immutable
@ResponseStyle
abstract public class AbstractFailResponse extends Response {
    abstract public String getReason();
    public boolean getOk() { return false; }
}
