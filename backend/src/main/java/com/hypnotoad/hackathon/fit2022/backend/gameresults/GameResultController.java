package com.hypnotoad.hackathon.fit2022.backend.gameresults;

import com.hypnotoad.hackathon.fit2022.backend.auth.token.PrimitiveTokensRepository;
import com.hypnotoad.hackathon.fit2022.backend.configurations.Strings;
import com.hypnotoad.hackathon.fit2022.backend.responses.FailResponse;
import com.hypnotoad.hackathon.fit2022.backend.responses.Response;
import com.hypnotoad.hackathon.fit2022.backend.responses.gameresults.AllGameResultsResponse;
import com.hypnotoad.hackathon.fit2022.backend.responses.gameresults.GameResultResponse;
import com.hypnotoad.hackathon.fit2022.backend.responses.gameresults.GameTotalResultResponse;
import com.hypnotoad.hackathon.fit2022.backend.responses.gameresults.LeaderboardResponse;
import com.hypnotoad.hackathon.fit2022.backend.utils.wrappers.GameRepoWrapper;
import com.hypnotoad.hackathon.fit2022.backend.utils.wrappers.ResponseWrapper;
import com.hypnotoad.hackathon.fit2022.backend.utils.wrappers.UserRepoWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GameResultController {
    final UserRepoWrapper userRepoWrapper;
    final PrimitiveTokensRepository primitiveTokensRepository;
    final GameResultRepository gameResultRepository;
    final GameRepoWrapper gameRepoWrapper;

    @Autowired Strings strings;
    static final Logger log = LoggerFactory.getLogger(GameResultController.class);

    @GetMapping("/api/getAllGameResultsByUser")
    public ResponseEntity<Response> getAllGameResultsByUser(@RequestParam String token) {
        log.debug("getAllGameResultsByUser issued with token {}", token);

        var user = userRepoWrapper.userByToken(token, true);
        if (user.isLeft()) return user.getLeft();

        var gameResults = gameResultRepository.findAllByUserId(user.get().getId());

        return ResponseEntity.status(200).body(new AllGameResultsResponse(gameResults));
    }

    @GetMapping("/api/getGameResultsByUser")
    public ResponseEntity<Response> getGameResultsByUser(@RequestParam String token, @RequestParam int gameId) {
        log.debug("getGameResultsByUser issued with token {}", token);

        var user = userRepoWrapper.userByToken(token, true);
        if (user.isLeft()) return user.getLeft();

        var gameResults = gameResultRepository.findAllByUserId(user.get().getId())
                .filter(g -> g.getGameId() == gameId);

        return ResponseEntity.status(200).body(new AllGameResultsResponse(gameResults));
    }

    @GetMapping("/api/addGameResult")
    public ResponseEntity<Response> addGameResult(@RequestParam String token,
            @RequestParam int gameId, @RequestParam boolean result,
            @RequestParam int score, @RequestParam float timeElapsed
    ) {
        log.debug("addGameResult issued with token {}", token);

        var user = userRepoWrapper.userByToken(token, true);
        if (user.isLeft()) return user.getLeft();

        var game = gameRepoWrapper.gameById(gameId);
        if (game.isLeft()) return game.getLeft();

        var gameResult = gameResultRepository.createGameResult(
                user.get().getId(), gameId, result, score, timeElapsed);
        if (gameResult.isEmpty()) return ResponseWrapper.fail(500, strings.somethingWentWrong);

        return ResponseEntity.status(200).body(new GameResultResponse(gameResult.get()));
    }

    @GetMapping("/api/getLeaderboard")
    public ResponseEntity<Response> getLeaderboard(@RequestParam String token, @RequestParam int gameId) {
        log.debug("getLeaderboard issued with token {}", token);

        var user = userRepoWrapper.userByToken(token, true);
        if (user.isLeft()) return user.getLeft();

        var game = gameRepoWrapper.gameById(gameId);
        if (game.isLeft()) return game.getLeft();

        var leaderboard = gameResultRepository.getLeaderboard(user.get().getId(), gameId);

        return ResponseEntity.status(200).body(new LeaderboardResponse(leaderboard));
    }

    @GetMapping("/api/getGameTotalResult")
    public ResponseEntity<Response> getGameTotalResult(
            @RequestParam String token,
            @RequestParam int gameId,
            @RequestParam int days
    ) {
        log.debug("getGameTotalResult issued with token {}", token);

        var user = userRepoWrapper.userByToken(token, true);
        if (user.isLeft()) return user.getLeft();

        var game = gameRepoWrapper.gameById(gameId);
        if (game.isLeft()) return game.getLeft();

        var gameResults = gameResultRepository.findGameTotalResultForDays(
                user.get().getId(), gameId, days);

        return ResponseEntity.status(200).body(new GameTotalResultResponse(gameResults));
    }

    public GameResultController(UserRepoWrapper userRepoWrapper,
                                PrimitiveTokensRepository primitiveTokensRepository,
                                GameResultRepository gameResultRepository,
                                GameRepoWrapper gameRepoWrapper
    ) {
        this.gameResultRepository = gameResultRepository;
        this.primitiveTokensRepository = primitiveTokensRepository;
        this.userRepoWrapper = userRepoWrapper;
        this.gameRepoWrapper = gameRepoWrapper;
    }
}
