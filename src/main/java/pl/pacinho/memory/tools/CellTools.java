package pl.pacinho.memory.tools;

import pl.pacinho.memory.model.enums.CellType;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

public class CellTools {

    private static List<CellType> getCells() {
        return Arrays.stream(CellType.values())
                .filter(ct -> ct!=CellType.EMPTY)
                .toList();
    }

    public static List<CellType> getGameCells() {
        return IntStream.rangeClosed(1,2)
                .boxed()
                .map( i -> getCells())
                .flatMap(List::stream)
                .toList();
    }
}
