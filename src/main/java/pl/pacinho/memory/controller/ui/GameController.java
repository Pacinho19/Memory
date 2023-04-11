package pl.pacinho.memory.controller.ui;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.pacinho.memory.config.UIConfig;
import pl.pacinho.memory.model.dto.AnswerRequestDto;
import pl.pacinho.memory.model.dto.GameDto;
import pl.pacinho.memory.model.enums.GameStatus;
import pl.pacinho.memory.service.GameService;

@RequiredArgsConstructor
@Controller
public class GameController {

    private final GameService gameService;

    @GetMapping(UIConfig.PREFIX)
    public String gameHome(Model model) {
        return "home";
    }

    @PostMapping(UIConfig.GAME)
    public String availableGames(Model model, Authentication authentication) {
        if (authentication != null)
            model.addAttribute("games", gameService.getAvailableGames(authentication.getName()));
        return "fragments/available-games :: availableGamesFrag";
    }

    @PostMapping(UIConfig.NEW_GAME)
    public String newGame(Model model, Authentication authentication) {
        try {
            return "redirect:" + UIConfig.GAME + "/" + gameService.newGame(authentication.getName()) + "/room";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return gameHome(model);
        }
    }

    @GetMapping(UIConfig.GAME_ROOM)
    public String gameRoom(@PathVariable(value = "gameId") String gameId, Model model, Authentication authentication) {
        try {
            GameDto game = gameService.findDtoById(gameId, authentication.getName());
            if (game.getStatus() == GameStatus.IN_PROGRESS)
                return "redirect:" + UIConfig.GAME + "/" + gameId;
            if (game.getStatus() == GameStatus.FINISHED)
                return "redirect:" + UIConfig.GAME + "/" + gameId + "/summary";

            model.addAttribute("game", game);
            model.addAttribute("joinGame", gameService.canJoin(game, authentication.getName()));
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return gameHome(model);
        }
        return "game-room";
    }

    @PostMapping(UIConfig.PLAYERS)
    public String players(@PathVariable(value = "gameId") String gameId,
                          Authentication authentication,
                          Model model) {
        GameDto game = gameService.findDtoById(gameId, authentication.getName());
        model.addAttribute("game", game);
        return "fragments/game-players :: gamePlayersFrag";
    }

    @GetMapping(UIConfig.GAME_PAGE)
    public String gamePage(@PathVariable(value = "gameId") String gameId,
                           Model model,
                           RedirectAttributes redirectAttr,
                           Authentication authentication) {

        GameDto game = gameService.findDtoById(gameId, authentication.getName());
        if (game.getStatus() == GameStatus.NEW) {
            model.addAttribute("game", game);
            return "redirect:" + UIConfig.GAME + "/" + gameService.newGame(authentication.getName()) + "/room";
        }

        if (game.getStatus() == GameStatus.FINISHED) {
            redirectAttr.addAttribute("gameId", gameId);
            return "redirect:" + UIConfig.GAME_SUMMARY;
        }

        model.addAttribute("game", game);
        return "game";
    }

    @GetMapping(UIConfig.GAME_BOARD_RELOAD)
    public String reloadBoard(Model model,
                              Authentication authentication,
                              @PathVariable(value = "gameId") String gameId) {
        model.addAttribute("game", gameService.findDtoById(gameId, authentication.getName()));
        return "fragments/board :: boardFrag";
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PostMapping(UIConfig.GAME_ANSWER)
    public void answer(@RequestBody AnswerRequestDto answerRequestDto,
                       @PathVariable(value = "gameId") String gameId) {
        gameService.answer(gameId, answerRequestDto);
    }

}