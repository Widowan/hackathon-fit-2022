package com.hypnotoad.hackathon.fit2022.backend.games;

import io.vavr.collection.List;
import io.vavr.control.Option;
import io.vavr.control.Try;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class GameRepository {
    final JdbcTemplate jdbc;
    static final Logger log = LoggerFactory.getLogger(GameRepository.class);

    private final RowMapper<Game> gameRowMapper;

    public Option<Game> findById(int id) {
        var sql = "SELECT * FROM Games WHERE id = ?";
        return Try.of(() -> jdbc.queryForObject(sql, gameRowMapper, id))
                .toOption();
    }

    public Option<Game> findByName(String name) {
        var sql = "SELECT * FROM Games WHERE lower(name) = lower(?)";
        return Try.of(() -> jdbc.queryForObject(sql, gameRowMapper, name))
                .toOption();
    }

    public List<Game> findAll() {
        var sql = "SELECT * FROM Games";
        return Try.of(() -> jdbc.query(sql, gameRowMapper))
                .onFailure(e -> log.error("GameRepository findAll", e))
                .map(List::ofAll)
                .getOrElse(List::empty);
    }

    public GameRepository(String staticUrl, JdbcTemplate jdbc) {
        this.jdbc = jdbc;
        this.gameRowMapper = (rs, rowNum) -> ImmutableGame.builder()
                .id(rs.getInt("id"))
                .name(rs.getString("name"))
                .description(rs.getString("description"))
                .rules(rs.getString("rules"))
                .icon(staticUrl + rs.getString("icon"))
                .build();
    }
}
