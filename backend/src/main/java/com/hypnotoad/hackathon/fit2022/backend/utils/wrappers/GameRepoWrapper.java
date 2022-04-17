package com.hypnotoad.hackathon.fit2022.backend.utils.wrappers;

import com.hypnotoad.hackathon.fit2022.backend.configurations.Strings;
import com.hypnotoad.hackathon.fit2022.backend.games.Game;
import com.hypnotoad.hackathon.fit2022.backend.games.GameRepository;
import com.hypnotoad.hackathon.fit2022.backend.responses.Response;
import io.vavr.control.Either;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class GameRepoWrapper {
    final GameRepository gameRepository;
    @Autowired Strings strings;

    public Either<ResponseEntity<Response>, Game> gameById(int id) {
        var game = gameRepository.findById(id);
        if (game.isEmpty()) return Either.left(ResponseWrapper.fail(401, strings.gameNotExists));
        return Either.right(game.get());
    }

    public GameRepoWrapper(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }
}
