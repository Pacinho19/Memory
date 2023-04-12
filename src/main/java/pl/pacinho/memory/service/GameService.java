package pl.pacinho.memory.service;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import pl.pacinho.memory.model.dto.AnswerRequestDto;
import pl.pacinho.memory.model.dto.CellDto;
import pl.pacinho.memory.model.dto.GameDto;
import pl.pacinho.memory.model.dto.mapper.GameDtoMapper;
import pl.pacinho.memory.model.entity.Game;
import pl.pacinho.memory.model.entity.Player;
import pl.pacinho.memory.model.enums.CellType;
import pl.pacinho.memory.model.enums.GameStatus;
import pl.pacinho.memory.repository.GameRepository;
import pl.pacinho.memory.utils.GameUtils;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

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
        return game.getPlayers().size() < PLAYERS_COUNT && game.getPlayers().stream().noneMatch(p -> p.name().equals(name));
    }

    public void answer(String gameId, AnswerRequestDto answerRequestDto, String playerName) {
        Game game = gameLogicService.findById(gameId);
        if (game.getStatus() == GameStatus.FINISHED)
            return;

        game.addAnswerIndex(answerRequestDto.idx());
        CellDto firstCell = game.getCells().get(game.getAnswerIndexes().get(0));
        if (game.getAnswerIndexes().size() == 1) {
            firstCell.setVisible(true);
            simpMessagingTemplate.convertAndSend("/reload-board/" + game.getId(), true);
            return;
        }

        CellDto secondCell = game.getCells().get(game.getAnswerIndexes().get(1));
        secondCell.setVisible(true);

        game.nextRound();

        simpMessagingTemplate.convertAndSend("/reload-board/" + game.getId(), true);

        if (firstCell.getCell() != secondCell.getCell()) {
            hideThread(game, firstCell, secondCell);
        } else {
            addCellToPlayer(game, playerName, firstCell.getCell());
            game.nextPlayer();
            game.incrementPlayersRound();
            gameLogicService.checkEndOfGame(game);
            simpMessagingTemplate.convertAndSend("/reload-board/" + game.getId(), true);
        }

    }

    private void addCellToPlayer(Game game, String playerName, CellType cell) {
        Player player = GameUtils.getPlayer(game, playerName);
        if (player == null)
            return;

        player.addCell(cell);
    }

    private void hideThread(Game game, CellDto firstCell, CellDto secondCell) {
        TimerTask task = new TimerTask() {
            public void run() {
                firstCell.setVisible(false);
                secondCell.setVisible(false);
                game.nextPlayer();
                game.incrementPlayersRound();
                simpMessagingTemplate.convertAndSend("/reload-board/" + game.getId(), true);
            }
        };

        new Timer("hideCellTask").schedule(task, 1000); //1 second

    }
}
