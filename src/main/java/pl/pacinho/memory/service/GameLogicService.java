package pl.pacinho.memory.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.pacinho.memory.exception.GameNotFoundException;
import pl.pacinho.memory.model.entity.Game;
import pl.pacinho.memory.repository.GameRepository;

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

    }
}
