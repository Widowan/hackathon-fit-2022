package com.hypnotoad.hackathon.fit2022.backend.auth.token;

import com.hypnotoad.hackathon.fit2022.backend.users.User;
import io.vavr.control.Option;
import io.vavr.control.Try;
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

    private final RowMapper<PrimitiveToken> tokenRowMapper = (rs, rowNum) -> ImmutablePrimitiveToken.builder()
            .userId(rs.getInt("user_id"))
            .token(rs.getString("token"))
            .expiryTimestamp(rs.getInt("timestamp"))
            .build();

    public Option<PrimitiveToken> findByUser(User user) {
        var sql = "SELECT * FROM UserTokens WHERE user_id = ?";

        return Try.of(() -> jdbc.queryForObject(sql, tokenRowMapper, user.getId()))
                .onFailure(e -> log.error("findByUser failed", e))
                .toOption();
    }

    public Option<PrimitiveToken> findByToken(String token) {
        var sql = "SELECT * FROM UserTokens WHERE token = ?";

        return Try.of(() -> jdbc.queryForObject(sql, tokenRowMapper, token))
                .onFailure(e -> log.error("findByToken failed", e))
                .toOption();
    }

    public Option<? extends PrimitiveToken> createToken(User user) {
        deleteTokenByUser(user);

        var token = primitiveTokenProvider.create();
        Option<Boolean> exists = validateTokenNoProlong(token);
        // Return None if db connection failed;
        if (exists.isEmpty()) return Option.none();
        // Retry until token is unique
        // NOTE: It checks one extra time
        while (validateTokenNoProlong(token).get()) token = primitiveTokenProvider.create();

        var pt = ImmutablePrimitiveToken.builder()
                .userId(user.getId())
                .token(token)
                .expiryTimestamp(Instant.now().getEpochSecond() + tokenLifespanSeconds)
                .build();

        var sql = "INSERT INTO UserTokens VALUES (?, ?, ?)";

        return Try.of(() -> jdbc.update(sql, pt.getUserId(), pt.getToken(), pt.getExpiryTimestamp()))
                .onFailure(e -> log.error("createToken failed:", e))
                .map(x -> pt)
                .toOption();
    }

    public Option<Boolean> deleteTokenByUser(User user) {
        return primitiveTokenProvider.expire(user);
    }

    public Option<Boolean> validateToken(String token) {
        var isValid = primitiveTokenProvider.isValid(token);
        if (isValid.isEmpty()) return isValid;

        var sql = """
                UPDATE UserTokens
                SET expire_time = extract(epoch FROM now()) + ?
                WHERE token = ?""";

        return Try.of(() -> jdbc.update(sql, tokenLifespanSeconds, token))
                .map(x -> x > 0)
                .toOption();
    }

    public Option<Boolean> validateTokenNoProlong(String token) {
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
