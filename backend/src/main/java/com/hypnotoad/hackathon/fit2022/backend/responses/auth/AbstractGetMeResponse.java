package com.hypnotoad.hackathon.fit2022.backend.responses.auth;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.hypnotoad.hackathon.fit2022.backend.configurations.ResponseStyle;
import com.hypnotoad.hackathon.fit2022.backend.responses.Response;
import com.hypnotoad.hackathon.fit2022.backend.users.User;
import org.immutables.value.Value;

@Value.Immutable
@ResponseStyle
abstract public class AbstractGetMeResponse extends Response {
    abstract User getMe();
}
