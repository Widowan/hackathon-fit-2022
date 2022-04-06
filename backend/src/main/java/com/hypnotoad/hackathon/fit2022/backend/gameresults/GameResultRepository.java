package com.hypnotoad.hackathon.fit2022.backend.gameresults;

import com.hypnotoad.hackathon.fit2022.backend.users.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class GameResultRepository {
    private final JdbcTemplate jdbc;
    private final RowMapper<GameResult> gameResultRowMapper;
    private final static Logger log = LoggerFactory.getLogger(GameResultRepository.class);

    public GameResult findById(int id) {
        var sql = "SELECT * FROM GameResults WHERE id = ?";

        try {
            return jdbc.queryForObject(sql, gameResultRowMapper, id);
        } catch (DataAccessException ignored) {}

        return null;
    }

    public List<GameResult> findAllByUserId(int userId) {
        var sql = "SELECT * FROM GameResults WHERE user_id = ?";
        try {
            return jdbc.query(sql, gameResultRowMapper, userId);
        } catch (DataAccessException e) {
            log.error("Couldn't access database: ", e);
        }

        return null;
    }

    public GameResult findLast() {
        var sql = "SELECT * FROM GameResults ORDER BY id DESC LIMIT 1";
        try {
            return jdbc.queryForObject(sql, gameResultRowMapper);
        } catch (DataAccessException ignored) {}

        return null;
    }

    public GameResult createGameResult(int userId, int gameId,
            boolean result, int score, float timeElapsed
    ) {
        var sql =
                "INSERT INTO " +
                    "GameResults(user_id, game_id, result, score, time_elapsed) " +
                "VALUES " +
                    "(?, ?, ?, ?, ?)";

        try {
            var upd = jdbc.update(sql, userId, gameId, result, score, timeElapsed);
            if (upd == 0) {
                log.error("Couldn't insert to database - 0 updated");
                return null;
            }
            return findLast();
        } catch (DataAccessException e) {
            log.error("Couldn't insert to database - data access exception");
        }

        return null;
    }

    public GameResultRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
        this.gameResultRowMapper = (rs, rowNum) -> {
            var rowGameResult = new GameResult();
            rowGameResult.setId(rs.getInt("id"));
            rowGameResult.setUserId(rs.getInt("user_id"));
            rowGameResult.setGameId(rs.getInt("game_id"));
            rowGameResult.setResult(rs.getBoolean("result"));
            rowGameResult.setScore(rs.getInt("score"));
            rowGameResult.setTimeElapsed(rs.getFloat("time_elapsed"));
            return rowGameResult;
        };
    }
}
