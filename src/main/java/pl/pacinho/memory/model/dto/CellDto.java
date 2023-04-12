package pl.pacinho.memory.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import pl.pacinho.memory.model.enums.CellType;


@Getter
@AllArgsConstructor
public class CellDto {
    private CellType cell;
    @Setter
    private boolean visible;
    private int index;

}
