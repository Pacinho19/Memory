package pl.pacinho.memory.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import pl.pacinho.memory.model.enums.GameStatus;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class GameDto {

    private String id;
    private GameStatus status;
    private List<PlayerInfoDto> players;
    private LocalDateTime startTime;
    private List<CellDto> cells;
    private boolean canPlay;
    private int actualPlayer;
    private List<Integer> answerIndexes;
    private String resultMessage;

}
