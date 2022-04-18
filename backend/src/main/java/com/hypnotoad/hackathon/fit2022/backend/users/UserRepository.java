package com.hypnotoad.hackathon.fit2022.backend.users;

import io.vavr.control.Option;
import io.vavr.control.Try;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {
    private final JdbcTemplate jdbc;
    static final Logger log = LoggerFactory.getLogger(UserRepository.class);

    private final RowMapper<User> userRowMapper;

    public UserRepository(String staticUrl, JdbcTemplate jdbc) {
        this.jdbc = jdbc;
        this.userRowMapper = (rs, rowNum) -> ImmutableUser.builder()
                .id(rs.getInt("id"))
                .username(rs.getString("username"))
                .avatar(rs.getString("avatar"))
                .build();
    }

    public Option<User> createUser(String username, String passwordHash) {
        var sql = "INSERT INTO Users(username, password_hash) VALUES (?, ?)";

        return Try.of(() -> jdbc.update(sql, username, passwordHash))
                .onFailure(e -> log.error("createUser", e))
                .flatMap(v -> findByUsername(username).toTry())
                .toOption();
    }

    public Option<User> findByUsername(String username) {
        var sql = "SELECT * FROM Users WHERE username = ?";

        return Try.of(() -> jdbc.queryForObject(sql, userRowMapper, username))
//                .onFailure(e -> log.error("findByUsername", e))
                .toOption();
    }

    public Option<User> findById(int id) {
        var sql = "SELECT * FROM Users WHERE id = ?";

        return Try.of(() -> jdbc.queryForObject(sql, userRowMapper, id))
//                .onFailure(e -> log.error("findById", e))
                .toOption();
    }

    public Option<User> findByToken(String token) {
        var sql = """
                SELECT Users.* FROM Users, UserTokens
                WHERE (UserTokens.token = ? AND Users.id = UserTokens.user_id)""";

        return Try.of(() -> jdbc.queryForObject(sql, userRowMapper, token))
//                .onFailure(e -> log.error("findByToken", e))
                .toOption();
    }

    public Option<Boolean> validatePasswordHashByUsername(String username, String passwordHash) {
        var sql = "SELECT password_hash FROM Users WHERE username = ?";

        return Try.of(() -> jdbc.queryForObject(sql, String.class, username))
//                .onFailure(e -> log.error("validatePasswordHashByUsername", e))
                .map(passwordHash::equals)
                .toOption();
    }

    public Option<Boolean> setAvatarByUserId(int id, String avatar) {
        var sql = "UPDATE Users SET avatar = '?' WHERE id = ?";

        return Try.of(() -> jdbc.update(sql, avatar, id))
                .onFailure(e -> log.error("setAvatarById", e))
                .map(u -> u > 0)
                .toOption();
    }
}
