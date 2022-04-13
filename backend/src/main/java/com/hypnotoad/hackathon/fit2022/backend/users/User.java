package com.hypnotoad.hackathon.fit2022.backend.users;

import com.hypnotoad.hackathon.fit2022.backend.configurations.JsonEntityStyle;
import org.immutables.value.Value;

@Value.Immutable
@JsonEntityStyle
abstract public class User {
    abstract public int getId();
    abstract public String getUsername();
    abstract public String getAvatar();
}
