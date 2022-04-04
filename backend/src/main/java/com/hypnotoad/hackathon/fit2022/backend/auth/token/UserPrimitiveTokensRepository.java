package com.hypnotoad.hackathon.fit2022.backend.auth.token;

import com.hypnotoad.hackathon.fit2022.backend.users.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.time.Instant;

@Repository
public class UserPrimitiveTokensRepository {
    private final JdbcTemplate jdbc;
    private final PrimitiveTokenProvider primitiveTokenProvider;
    @Value("${auth.tokenLifespanSeconds}")
    private long tokenLifespanSeconds;

    static final Logger log = LoggerFactory.getLogger(UserPrimitiveTokensRepository.class);

    private final RowMapper<PrimitiveToken> tokenRowMapper = (rs, rowNum) -> {
        var pt = new PrimitiveToken();
        pt.setUserId(rs.getInt("user_id"));
        pt.setToken(rs.getString("token"));
        pt.setExpiryTimestamp(rs.getLong("timestamp"));
        return pt;
    };

    public PrimitiveToken findByUser(User user) {
        var sql = "SELECT * FROM UserTokens WHERE user_id = ?";

        try {
            return jdbc.queryForObject(sql, tokenRowMapper, user.getId());
        } catch (DataAccessException ignored) {}

        return null;
    }

    public PrimitiveToken findByToken(String token) {
        var sql = "SELECT * FROM UserTokens WHERE token = ?";

        try {
            return jdbc.queryForObject(sql, tokenRowMapper, token);
        } catch (DataAccessException ignored) {}

        return null;
    }

    public PrimitiveToken createToken(User user) {
        log.debug("Creating token for user: {}", user);
        deleteTokenByUser(user);

        var token = primitiveTokenProvider.create();
        while (validateTokenNoProlong(token)) token = primitiveTokenProvider.create();

        var pt = new PrimitiveToken();
        pt.setToken(token);
        pt.setUserId(user.getId());
        pt.setExpiryTimestamp(Instant.now().getEpochSecond() + tokenLifespanSeconds);

        var sql = "INSERT INTO UserTokens VALUES (?, ?, ?)";

        try {
            jdbc.update(sql, pt.getUserId(), pt.getToken(), pt.getExpiryTimestamp());
        } catch (DataAccessException e) {
            log.error("Couldn't insert token into DB", e);
        }

        return pt;
    }

    public boolean deleteTokenByUser(User user) {
        return primitiveTokenProvider.expire(user);
    }

    public boolean validateToken(String token) {
        var isValid = primitiveTokenProvider.isValid(token);
        if (!isValid) return false;

        var sql = "UPDATE UserTokens " +
                "SET   expire_time = extract(epoch FROM now()) + ? " +
                "WHERE token = ?";
        try {
            return jdbc.update(sql, tokenLifespanSeconds, token) > 0;
        } catch (DataAccessException ignored) {}

        return true;
    }

    public boolean validateTokenNoProlong(String token) {
        return primitiveTokenProvider.isValid(token);
    }

    public UserPrimitiveTokensRepository(
            JdbcTemplate jdbc,
            PrimitiveTokenProvider primitiveTokenProvider
    ) {
        this.jdbc = jdbc;
        this.primitiveTokenProvider = primitiveTokenProvider;
    }
}
