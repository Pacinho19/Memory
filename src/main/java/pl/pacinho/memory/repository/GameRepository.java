package pl.pacinho.memory.repository;

import org.springframework.stereotype.Repository;
import pl.pacinho.memory.exception.GameNotFoundException;
import pl.pacinho.memory.model.dto.GameDto;
import pl.pacinho.memory.model.dto.mapper.GameDtoMapper;
import pl.pacinho.memory.model.entity.Game;
import pl.pacinho.memory.model.entity.Player;
import pl.pacinho.memory.model.enums.GameStatus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class GameRepository {
    private Map<String, Game> gameMap;

    public GameRepository() {
        gameMap = new HashMap<>();
    }

    public Game newGame(String playerName) {
        Game game = new Game(playerName);
        gameMap.put(game.getId(), game);
        return game;
    }

    public List<GameDto> getAvailableGames(String name) {
        return gameMap.values()
                .stream()
                .filter(game -> game.getStatus() != GameStatus.FINISHED)
                .map(g -> GameDtoMapper.parse(g, name))
                .toList();
    }

    public Game joinGame(String name, String gameId) throws IllegalStateException {
        Game game = gameMap.get(gameId);
        if (game == null)
            throw new GameNotFoundException(gameId);

        if (game.getStatus() != GameStatus.NEW)
            throw new IllegalStateException("Cannot join to " + gameId + ". Game status : " + game.getStatus());

        if (game.getPlayers().get(0).getName().equals(name))
            throw new IllegalStateException("Game " + gameId + " was created by you!");

        game.getPlayers().add(new Player(name, game.getPlayers().size() + 1));
        return game;
    }

    public Optional<Game> findById(String gameId) {
        return Optional.ofNullable(gameMap.get(gameId));
    }
}