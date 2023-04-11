package pl.pacinho.memory.controller.ui;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import pl.pacinho.memory.config.UIConfig;

@RequiredArgsConstructor
@Controller
public class HomeController {

    @GetMapping
    public String home2(){
        return "redirect:" + UIConfig.PREFIX;
    }
}
