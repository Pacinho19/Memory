package pl.pacinho.memory.model.entity;

import lombok.Getter;
import lombok.Setter;
import pl.pacinho.memory.model.dto.CellDto;
import pl.pacinho.memory.model.enums.GameStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

@Getter
public class Game {

    private String id;
    @Setter
    private GameStatus status;
    private LinkedList<Player> players;
    private LocalDateTime startTime;
    @Setter
    private List<CellDto> cells;
    private int actualPlayer;
    private int round;
    private List<Integer> answerIndexes;
    @Setter
    private String result;

    public Game(String player1) {
        players = new LinkedList<>();
        players.add(new Player(player1, 1));
        this.id = UUID.randomUUID().toString();
        this.status = GameStatus.NEW;
        this.startTime = LocalDateTime.now();
        this.cells = new ArrayList<>();
        this.actualPlayer = 1;
        this.round = 1;
        this.answerIndexes = new ArrayList<>();
    }

    public void addCell(CellDto cell) {
        this.cells.add(cell);
    }

    public void nextPlayer() {
        this.answerIndexes.clear();

        if (actualPlayer == players.size())
            actualPlayer = 1;
        else
            actualPlayer++;
    }

    public void nextRound() {
        this.round++;
    }

    public void addAnswerIndex(int index) {
        this.answerIndexes.add(index);
    }

    public void incrementPlayersRound() {
        players.forEach(Player::nextRound);
    }
}
