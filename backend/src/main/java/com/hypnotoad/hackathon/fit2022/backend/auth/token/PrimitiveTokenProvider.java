package com.hypnotoad.hackathon.fit2022.backend.auth.token;

import com.hypnotoad.hackathon.fit2022.backend.users.User;
import io.vavr.control.Option;
import io.vavr.control.Try;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;

@Component
class PrimitiveTokenProvider {
    final JdbcTemplate jdbc;
    @Autowired int tokenLen;
    static final Logger log = LoggerFactory.getLogger(PrimitiveTokenProvider.class);

    public PrimitiveTokenProvider(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public Option<Boolean> isValid(String token) {
        var sql = """
                SELECT COUNT(*)
                FROM  UserTokens
                WHERE token = ?
                   AND expiry_time > extract(epoch FROM now())""";

        return Try.of(() -> jdbc.queryForObject(sql, Integer.class, token))
//                .onFailure(e -> log.error("token isValid", e))
                .map(x -> x > 0)
                .toOption();
    }

    public String create() {
        return randomToken(tokenLen);
    }

    final static String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    private String randomToken(int len) {
        var sr = new SecureRandom();
        var sb = new StringBuilder();

        for (int i = 0; i < len; i++)
            sb.append(chars.charAt(sr.nextInt(chars.length())));

        return sb.toString();
    }

    public Option<Boolean> expire(User user) {
        var sql = """
                UPDATE UserTokens
                SET expiry_time = extract(epoch FROM now()) - 1
                WHERE user_id = ?
                    AND expiry_time > extract(epoch FROM now())""";

        return Try.of(() -> jdbc.update(sql, user.getId()))
                .onFailure(e -> log.error("token expire", e))
                .map(x -> x > 0)
                .toOption();
    }
}
