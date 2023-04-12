package pl.pacinho.memory.model.dto;

import pl.pacinho.memory.model.enums.CellType;

import java.util.List;

public record PlayerInfoDto(String name, int idx, List<CellType> cells) {
}
