package pl.pacinho.memory.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class JoinGameDto {

    private String name;
    private boolean start;
    private String exception;
}