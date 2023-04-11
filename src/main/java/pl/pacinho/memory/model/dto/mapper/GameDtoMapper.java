package pl.pacinho.memory.model.dto.mapper;

import pl.pacinho.memory.model.dto.CellDto;
import pl.pacinho.memory.model.dto.GameDto;
import pl.pacinho.memory.model.entity.Game;
import pl.pacinho.memory.model.entity.Player;
import pl.pacinho.memory.model.enums.CellType;

import java.util.List;

public class GameDtoMapper {

    public static GameDto parse(Game game, String name) {
        return GameDto.builder()
                .id(game.getId())
                .startTime(game.getStartTime())
                .players(game.getPlayers().stream().map(Player::getName).sorted().toList())
                .status(game.getStatus())
                .cells(
                        getGameCells(game)
                )
                .build();
    }

    private static List<CellDto> getGameCells(Game game) {
        return game.getCells()
                .stream()
                .map(c -> c.visible() ? c : new CellDto(CellType.EMPTY, false, c.index()))
                .toList();
    }

}