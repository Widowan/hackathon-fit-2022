package com.hypnotoad.hackathon.fit2022.backend.games;

import com.hypnotoad.hackathon.fit2022.backend.configurations.Strings;
import com.hypnotoad.hackathon.fit2022.backend.responses.Response;
import com.hypnotoad.hackathon.fit2022.backend.responses.game.AllGamesResponse;
import com.hypnotoad.hackathon.fit2022.backend.responses.game.GameResponse;
import com.hypnotoad.hackathon.fit2022.backend.utils.wrappers.ResponseWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GameController {
    GameRepository gameRepository;
    @Autowired Strings strings;
    static final Logger log = LoggerFactory.getLogger(GameController.class);

    @GetMapping("/api/getGameById")
    public ResponseEntity<Response> getGameById(@RequestParam int id) {
        log.debug("getGameById issued with id {}", id);

        var game = gameRepository.findById(id).onEmpty(() -> log.debug(strings.gameNotExists));
        if (game.isEmpty()) return ResponseWrapper.fail(401, strings.gameNotExists);

        return ResponseEntity.status(200).body(new GameResponse(game.get()));
    }

    @GetMapping("/api/getGameByName")
    public ResponseEntity<Response> getGameByName(@RequestParam String name) {
        log.debug("getGameByName issued with name {}", name);

        var game = gameRepository.findByName(name).onEmpty(() -> log.debug(strings.gameNotExists));
        if (game.isEmpty()) return ResponseWrapper.fail(401, strings.gameNotExists);

        return ResponseEntity.status(200).body(new GameResponse(game.get()));
    }

    @GetMapping("/api/getAllGames")
    public ResponseEntity<Response> getAllGames() {
        log.debug("getAllGames issued");
        var games = gameRepository.findAll();
        return ResponseEntity.status(200).body(new AllGamesResponse(games));
    }

    public GameController(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }
}