package pl.pacinho.memory.model.dto;

import pl.pacinho.memory.model.enums.CellType;

public record CellDto(CellType cell, boolean visible, int index) {
}
