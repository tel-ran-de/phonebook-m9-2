package com.telran.phonebookapi.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
public class UserController {

    @PostMapping("/api/user/registration")
    @CrossOrigin
    public void registration(@RequestBody String body) {
        // нужем сервис создать нового юзера
        System.out.println(body);
    }

    @GetMapping("/api/user/activation")
    @CrossOrigin
    public void activation(@RequestParam(name="token") String token) {
        // нужен сервис подтверждения юзера
        System.out.println(token);
    }

}
