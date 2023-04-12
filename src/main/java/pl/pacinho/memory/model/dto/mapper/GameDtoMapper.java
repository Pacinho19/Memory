package pl.pacinho.memory.model.dto.mapper;

import pl.pacinho.memory.model.dto.CellDto;
import pl.pacinho.memory.model.dto.GameDto;
import pl.pacinho.memory.model.dto.PlayerInfoDto;
import pl.pacinho.memory.model.entity.Game;
import pl.pacinho.memory.model.entity.Player;
import pl.pacinho.memory.model.enums.CellType;
import pl.pacinho.memory.model.enums.GameStatus;
import pl.pacinho.memory.utils.GameUtils;

import java.util.List;

public class GameDtoMapper {

    public static GameDto parse(Game game, String name) {
        return GameDto.builder()
                .id(game.getId())
                .startTime(game.getStartTime())
                .players(
                        getPlayersInfo(game)
                )
                .status(game.getStatus())
                .cells(
                        getGameCells(game)
                )
                .canPlay(
                        checkPlayerCanPlay(game, name)
                )
                .actualPlayer(game.getActualPlayer())
                .answerIndexes(game.getAnswerIndexes())
                .resultMessage(game.getResult())
                .build();
    }

    private static List<PlayerInfoDto> getPlayersInfo(Game game) {
        return game.getPlayers()
                .stream()
                .map(p -> new PlayerInfoDto(p.getName(), p.getIndex(), p.getCells()))
                .toList();
    }

    private static boolean checkPlayerCanPlay(Game game, String name) {
        if (game.getStatus() != GameStatus.IN_PROGRESS)
            return false;

        Player player = GameUtils.getPlayer(game, name);
        if (player == null)
            return false;

        return player.getIndex() == game.getActualPlayer()
               && player.getRound() == game.getRound();
    }


    private static List<CellDto> getGameCells(Game game) {
        return game.getCells()
                .stream()
                .map(c -> c.isVisible() ? c : new CellDto(CellType.EMPTY, false, c.getIndex()))
                .toList();
    }

}