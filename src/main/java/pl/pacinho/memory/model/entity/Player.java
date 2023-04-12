package pl.pacinho.memory.model.entity;

import lombok.Getter;
import pl.pacinho.memory.model.enums.CellType;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Player {
    private final String name;
    private int index;
    private int round;
    private List<CellType> cells;

    public Player(String name, int index) {
        this.name = name;
        this.index = index;
        this.round = 1;
        this.cells = new ArrayList<>();
    }

    public void nextRound() {
        this.round++;
    }

    public void addCell(CellType cellType) {
        this.cells.add(cellType);
    }
}