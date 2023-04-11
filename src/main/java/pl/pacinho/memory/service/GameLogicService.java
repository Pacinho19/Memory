package pl.pacinho.memory.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.pacinho.memory.exception.GameNotFoundException;
import pl.pacinho.memory.model.dto.CellDto;
import pl.pacinho.memory.model.entity.Game;
import pl.pacinho.memory.model.enums.CellType;
import pl.pacinho.memory.repository.GameRepository;
import pl.pacinho.memory.tools.CellTools;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

@RequiredArgsConstructor
@Service
public class GameLogicService {

    private final GameRepository gameRepository;

    public Game findById(String gameId) {
        return gameRepository.findById(gameId)
                .orElseThrow(() -> new GameNotFoundException(gameId))
                ;
    }

    public void initMemory(Game game) {
        List<CellType> gameCells = new ArrayList<>(CellTools.getGameCells());
        Collections.shuffle(gameCells);

        IntStream.range(0, gameCells.size())
                .boxed()
                .forEach(i -> game.addCell(new CellDto(gameCells.get(i), false, i)));
    }
}
