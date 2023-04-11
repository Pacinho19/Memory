package pl.pacinho.memory.config;

public class UIConfig {
    public static final String PREFIX = "/memory";
    public static final String GAME = PREFIX + "/game";
    public static final String NEW_GAME = GAME + "/new";
    public static final String GAME_PAGE = GAME + "/{gameId}";
    public static final String GAME_ROOM = GAME_PAGE + "/room";
    public static final String PLAYERS = GAME_ROOM + "/players";
    public static final String GAME_ANSWER = GAME_PAGE + "/answer";
    public static final String GAME_BOARD = GAME_PAGE + "/board";
    public static final String GAME_BOARD_RELOAD = GAME_BOARD + "/reload";
    public static final String GAME_SUMMARY = GAME_PAGE + "/summary";

}