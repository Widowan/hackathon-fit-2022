package com.hypnotoad.hackathon.fit2022.backend.responses.auth;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.hypnotoad.hackathon.fit2022.backend.configurations.ResponseStyle;
import com.hypnotoad.hackathon.fit2022.backend.responses.Response;
import org.immutables.value.Value;

@Value.Immutable
@ResponseStyle
abstract public class AbstractAuthResponse extends Response {
    abstract String getToken();
}
