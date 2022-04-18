package com.hypnotoad.hackathon.fit2022.backend.gameresults;

import io.vavr.collection.List;
import io.vavr.control.Option;
import io.vavr.control.Try;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class GameResultRepository {
    private final JdbcTemplate jdbc;
    private final RowMapper<GameResult> gameResultRowMapper;
    private final RowMapper<LeaderboardRow> leaderboardRowRowMapper;
    private final RowMapper<GameTotalResult> totalResultRowMapper;
    private final static Logger log = LoggerFactory.getLogger(GameResultRepository.class);

    public Option<GameResult> findById(int id) {
        var sql = "SELECT * FROM GameResults WHERE id = ?";
        return Try.of(() -> jdbc.queryForObject(sql, gameResultRowMapper, id))
                .toOption();
    }

    public List<GameResult> findAllByUserId(int userId) {
        var sql = "SELECT * FROM GameResults WHERE user_id = ?";
        return Try.of(() -> jdbc.query(sql, gameResultRowMapper, userId))
                .onFailure(e -> log.error("findAllByUserId", e))
                .map(List::ofAll)
                .getOrElse(List::empty);
    }

    public Option<GameResult> findLast() {
        var sql = "SELECT * FROM GameResults ORDER BY id DESC LIMIT 1";
        return Try.of(() -> jdbc.queryForObject(sql, gameResultRowMapper))
                .toOption();
    }

    public Option<GameResult> createGameResult(int userId, int gameId,
            boolean result, int score, float timeElapsed
    ) {
        var sql = """
                INSERT INTO
                    GameResults(user_id, game_id, result, score, time_elapsed)
                VALUES
                    (?, ?, ?, ?, ?)""";

        return Try.of(() -> jdbc.update(sql, userId, gameId, result, score, timeElapsed))
                .onFailure(e -> log.error("createGameResult", e))
                .filterTry(
                        u  -> u > 0,
                        () -> new DataRetrievalFailureException("Zero records updated")
                )
                .flatMap(z -> findLast().toTry())
                .toOption();
    }

    public List<LeaderboardRow> getLeaderboard(int userId, int gameId) {
        var sql = """
                WITH SummedScoreTable AS (
                    SELECT
                        user_id,
                        game_id,
                        SUM(score) AS sum_score
                    FROM GameResults
                    WHERE game_id = ?
                    GROUP BY user_id, game_id
                ),
                PlacedScoreBoard AS (
                    SELECT
                        user_id,
                        Row_Number() OVER (ORDER BY sum_score DESC) AS place,
                        sum_score
                    FROM SummedScoreTable
                ),
                UsernameTable AS (
                    SELECT
                        id,
                        username
                    FROM Users
                )
                SELECT
                    user_id, place, sum_score, username
                FROM
                    PlacedScoreboard p
                LEFT JOIN UsernameTable u
                    ON p.user_id = u.id
                WHERE
                    place <= 10
                    OR p.user_id = ?
                ORDER BY
                    place
                ;
                """;

        return Try.of(() -> jdbc.query(sql, leaderboardRowRowMapper, gameId, userId))
                .onFailure(e -> log.error("getLeaderboard", e))
                .map(List::ofAll)
                .getOrElse(List::empty);
    }

    public GameTotalResult findGameTotalResultForDays(int userId, int gameId, int days) {
        var sql = """
                WITH DatesTable AS (
                    SELECT
                        user_id,
                        game_id,
                        score,
                        date_timestamp
                    FROM GameResults
                    WHERE (extract(epoch from now())::Integer - date_timestamp) < ?
                )
                SELECT
                    user_id,
                    game_id,
                    SUM(score) as sum_score
                FROM DatesTable
                GROUP BY user_id, game_id
                HAVING user_id = ? AND game_id = ?
                """;

        return Try.of(() -> jdbc.queryForObject(sql, totalResultRowMapper,
                days*24*60*60, userId, gameId))
                .toOption()
                .getOrElse(ImmutableGameTotalResult.builder()
                        .userId(userId)
                        .gameId(gameId)
                        .sumScore(0)
                        .build());
    }

    public GameResultRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
        this.gameResultRowMapper = (rs, rowNum) -> ImmutableGameResult.builder()
                .id(rs.getInt("id"))
                .userId(rs.getInt("user_id"))
                .gameId(rs.getInt("game_id"))
                .result(rs.getBoolean("result"))
                .score(rs.getInt("score"))
                .timeElapsed(rs.getFloat("time_elapsed"))
                .dateTimestamp(rs.getInt("date_timestamp"))
                .build();
        this.leaderboardRowRowMapper = (rs, rowNum) -> ImmutableLeaderboardRow.builder()
                .userId(rs.getInt("user_id"))
                .place(rs.getInt("place"))
                .sumScore(rs.getInt("sum_score"))
                .username(rs.getString("username"))
                .build();
        this.totalResultRowMapper = (rs, rowNum) -> ImmutableGameTotalResult.builder()
                .userId(rs.getInt("user_id"))
                .gameId(rs.getInt("game_id"))
                .sumScore(rs.getInt("sum_score"))
                .build();
    }
}
