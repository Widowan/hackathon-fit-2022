package com.hypnotoad.hackathon.fit2022.backend.games;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class GameRepository {
    private final JdbcTemplate jdbc;
    static final Logger log = LoggerFactory.getLogger(GameRepository.class);


    private final RowMapper<Game> gameRowMapper = (rs, rowNum) -> {
        Game rowGame = new Game();
        rowGame.setId(rs.getInt("id"));
        rowGame.setName(rs.getString("name"));
        rowGame.setDescription(rs.getString("description"));
        rowGame.setRules(rs.getString("rules"));
        return rowGame;
    };

    public Game findById(int id) {
        var sql = "SELECT * FROM Games WHERE id = ?";

        try {
            return jdbc.queryForObject(sql, gameRowMapper, id);
        } catch (DataAccessException ignored) {}

        return null;
    }

    public Game findByName(String name) {
        var sql = "SELECT * FROM Games WHERE lower(name) = lower(?)";

        try {
            return jdbc.queryForObject(sql, gameRowMapper, name);
        } catch (DataAccessException ignored) {}

        return null;
    }

    public List<Game> findAll() {
        var sql = "SELECT * FROM Games";

        try {
            // Maybe I should've created `GameShort` object or something...
            return jdbc.query(sql, gameRowMapper);
        } catch (DataAccessException e) {
            log.error("Couldn't access database: ", e);
            return null;
        }
    }

    public GameRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }
}
