package pl.pacinho.memory.utils;

import pl.pacinho.memory.model.entity.Game;
import pl.pacinho.memory.model.entity.Player;

public class GameUtils {
    public static Player getPlayer(Game game, String playerName) {
        return game.getPlayers()
                .stream()
                .filter(p -> p.getName().equals(playerName))
                .findFirst()
                .orElse(null);
    }
}
