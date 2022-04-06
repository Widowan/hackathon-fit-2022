package com.hypnotoad.hackathon.fit2022.backend.gameresults;

import com.hypnotoad.hackathon.fit2022.backend.auth.token.UserPrimitiveTokensRepository;
import com.hypnotoad.hackathon.fit2022.backend.responses.FailResponse;
import com.hypnotoad.hackathon.fit2022.backend.responses.Response;
import com.hypnotoad.hackathon.fit2022.backend.responses.gameresults.AllGameResultsResponse;
import com.hypnotoad.hackathon.fit2022.backend.responses.gameresults.GameResultResponse;
import com.hypnotoad.hackathon.fit2022.backend.users.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GameResultController {
    UserRepository userRepository;
    UserPrimitiveTokensRepository userPrimitiveTokensRepository;
    GameResultRepository gameResultRepository;
    static final Logger log = LoggerFactory.getLogger(GameResultController.class);

    @GetMapping("/api/getAllGamesByUser")
    public ResponseEntity<Response> getAllGamesByUser(@RequestParam String token) {
        log.debug("getAllGamesByUser issued with token {}", token);

        var valid = userPrimitiveTokensRepository.validateToken(token);
        if (!valid) {
            log.debug("Provided token is invalid");
            return ResponseEntity.status(401).body(new FailResponse("Invalid token"));
        }

        var user = userRepository.findByToken(token);
        var gameResults = gameResultRepository.findAllByUserId(user.getId());
        if (gameResults == null) {
            log.debug("Found null, expected empty list");
            return ResponseEntity.status(500).body(new FailResponse("Couldn't access the database"));
        }

        return ResponseEntity.status(200).body(new AllGameResultsResponse(gameResults));
    }

    // SECURITY: No bounds check
    @PostMapping("/api/addGameResult")
    public ResponseEntity<Response> addGameResult(@RequestParam String token,
            @RequestParam int gameId, @RequestParam boolean result,
            @RequestParam int score, @RequestParam float timeElapsed
    ) {
        log.debug("addGameResult issued with token {}", token);

        var valid = userPrimitiveTokensRepository.validateToken(token);
        if (!valid) {
            log.debug("Provided token is invalid");
            return ResponseEntity.status(401).body(new FailResponse("Invalid token"));
        }

        var user = userRepository.findByToken(token);
        var gameResult = gameResultRepository.createGameResult(
                user.getId(), gameId, result, score, timeElapsed);
        if (gameResult == null) {
            return ResponseEntity.status(500).body(new FailResponse("Couldn't create database entry"));
        }

        return ResponseEntity.status(200).body(new GameResultResponse(gameResult));
    }

    public GameResultController(UserRepository userRepository,
            UserPrimitiveTokensRepository userPrimitiveTokensRepository,
            GameResultRepository gameResultRepository
    ) {
        this.gameResultRepository = gameResultRepository;
        this.userPrimitiveTokensRepository = userPrimitiveTokensRepository;
        this.userRepository = userRepository;
    }
}
