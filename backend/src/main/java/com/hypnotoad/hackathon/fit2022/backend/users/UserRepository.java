package com.hypnotoad.hackathon.fit2022.backend.users;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
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

    public User createUser(String username, String passwordHash) {
        var sql = "INSERT INTO Users(username, password_hash) VALUES (?, ?)";
        try {
            jdbc.update(sql, username, passwordHash);
            return findByUsername(username);
        } catch (DataAccessException e) {
            log.error("Couldn't create user", e);
        }

        return null;
    }

    public User findByUsername(String username) {
        var sql = "SELECT * FROM Users WHERE username = ?";

        try {
            return jdbc.queryForObject(sql, userRowMapper, username);
        } catch (DataAccessException ignored) {}

        return null;
    }

    public User findById(int id) {
        var sql = "SELECT * FROM Users WHERE id = ?";

        try {
            return jdbc.queryForObject(sql, userRowMapper, id);
        } catch (DataAccessException ignored) {}

        return null;
    }

    public User findByToken(String token) {
        var sql = "SELECT Users.* FROM Users, UserTokens " +
                "WHERE (UserTokens.token = ? AND Users.id = UserTokens.user_id)";

        try {
            return jdbc.queryForObject(sql, userRowMapper, token);
        } catch (DataAccessException ignored) {}

        return null;
    }

    public boolean validatePasswordHashByUsername(String username, String passwordHash) {
        var sql = "SELECT password_hash FROM Users WHERE username = ?";

        try {
            return passwordHash.equals(jdbc.queryForObject(sql, String.class, username));
        } catch (DataAccessException ignored) {}

        return false;
    }

    public boolean setAvatarByUserId(int id, String avatar) {
        var sql = "UPDATE Users SET avatar = '?' WHERE id = ?";

        try {
            return jdbc.update(sql, avatar, id) > 0;
        } catch (DataAccessException ignored) {}

        return false;
    }
}
