package com.hypnotoad.hackathon.fit2022.backend.configurations;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Strings {
    @Value("${strings.auth.emptyFields}")
    public String emptyFields;
    @Value("${strings.auth.userExists}")
    public String userExists;
    @Value("${strings.auth.userNotExists}")
    public String userNotExists;
    @Value("${strings.auth.cantCreateUser}")
    public String cantCreateUser;
    @Value("${strings.auth.cantCreateToken}")
    public String cantCreateToken;
    @Value("${strings.auth.invalidCredentials}")
    public String invalidCredentials;
    @Value("${strings.auth.invalidToken}")
    public String invalidToken;
    @Value("${strings.auth.tokenValidationFailed}")
    public String tokenValidationFailed;
    @Value("${strings.auth.tokenNotDeleted}")
    public String tokenNotDeleted;
    @Value("${strings.auth.passwordValidationFailed}")
    public String passwordValidationFailed;
    @Value("${strings.auth.usernameUnique}")
    public String usernameUnique;
    @Value("${strings.auth.usernameNotUnique}")
    public String usernameNotUnique;
    @Value("${strings.auth.avatarSetFail}")
    public String avatarSetFail;
    @Value("${strings.general.somethingWentWrong}")
    public String somethingWentWrong;
}
