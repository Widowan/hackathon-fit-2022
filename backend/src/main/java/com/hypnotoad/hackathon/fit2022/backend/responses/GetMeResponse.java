package com.hypnotoad.hackathon.fit2022.backend.responses;

import com.hypnotoad.hackathon.fit2022.backend.users.User;

public class GetMeResponse extends Response {
    private User Me;

    public GetMeResponse(User user) {
        this.ok = true;
        this.Me = user;
    }

    public User getMe() {
        return Me;
    }

    public void setMe(User me) {
        this.Me = me;
    }
}
