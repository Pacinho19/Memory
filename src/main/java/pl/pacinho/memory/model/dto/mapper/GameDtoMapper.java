package pl.pacinho.memory.model.dto.mapper;

import pl.pacinho.memory.model.dto.GameDto;
import pl.pacinho.memory.model.entity.Game;
import pl.pacinho.memory.model.entity.Player;

public class GameDtoMapper {

    public static GameDto parse(Game game, String name) {
        return GameDto.builder()
                .id(game.getId())
                .startTime(game.getStartTime())
                .players(game.getPlayers().stream().map(Player::getName).sorted().toList())
                .status(game.getStatus())
                .build();
    }

}