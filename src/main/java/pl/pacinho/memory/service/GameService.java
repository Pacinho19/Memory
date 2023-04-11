package pl.pacinho.memory.service;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import pl.pacinho.memory.model.dto.AnswerRequestDto;
import pl.pacinho.memory.model.dto.GameDto;
import pl.pacinho.memory.model.dto.mapper.GameDtoMapper;
import pl.pacinho.memory.model.entity.Game;
import pl.pacinho.memory.model.enums.GameStatus;
import pl.pacinho.memory.repository.GameRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
public class GameService {

    private final int PLAYERS_COUNT = 2;
    private final GameRepository gameRepository;
    private final GameLogicService gameLogicService;
    private final SimpMessagingTemplate simpMessagingTemplate;

    public List<GameDto> getAvailableGames(String playerName) {
        return gameRepository.getAvailableGames(playerName);
    }

    public String newGame(String playerName) {
        List<GameDto> activeGames = getAvailableGames(playerName);
        if (activeGames.size() >= 10)
            throw new IllegalStateException("Cannot create new Game! Active game count : " + activeGames.size());

        Game game = gameRepository.newGame(playerName);
        return game.getId();
    }

    public GameDto findDtoById(String gameId, String name) {
        return GameDtoMapper.parse(gameLogicService.findById(gameId), name);
    }

    public void joinGame(String name, String gameId) throws IllegalStateException {
        Game game = gameRepository.joinGame(name, gameId);
        if (game.getPlayers().size() == PLAYERS_COUNT) game.setStatus(GameStatus.IN_PROGRESS);
    }

    public boolean checkStartGame(String gameId) {
        Game game = gameLogicService.findById(gameId);
        boolean startGame = game.getPlayers().size() == PLAYERS_COUNT;

        if (startGame) gameLogicService.initMemory(game);
        return startGame;
    }

    public boolean canJoin(GameDto game, String name) {
        return game.getPlayers().size() < PLAYERS_COUNT && game.getPlayers().stream().noneMatch(p -> p.equals(name));
    }

    public void answer(String gameId, AnswerRequestDto answerRequestDto) {
        Game game = gameLogicService.findById(gameId);
        if (game.getStatus() == GameStatus.FINISHED)
            return;

    }
}
