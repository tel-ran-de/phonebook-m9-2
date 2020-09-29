package com.telran.phonebookapi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class RedirectUiController {

    @RequestMapping(value = {"{_:^(?!index\\.html|api)[^.]*$}", "/**/{_:[^.]*}"})
    public String toRedirect() {
        return "forward:/";
    }
}
