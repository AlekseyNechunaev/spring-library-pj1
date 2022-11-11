package ru.nechunaev.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import ru.nechunaev.constant.Paths;

@Controller
public class MainController {

    @GetMapping(Paths.INDEX)
    public String hello() {
        return "index";
    }
}
