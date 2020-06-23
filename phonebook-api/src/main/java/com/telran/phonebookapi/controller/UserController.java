package com.telran.phonebookapi.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
public class UserController {

    @PostMapping("/api/user/registration")
    @CrossOrigin
    public void addUser(@RequestBody String body) {
        System.out.println(body);
    }

    @GetMapping("/api/user/activation")
    @CrossOrigin
    public void activateUser(@RequestParam(name = "token") String token) {
        System.out.println(token);
    }

    @PostMapping("/api/user/login")
    @CrossOrigin
    public void loginUser(@RequestBody String body) {
        System.out.println(body);
    }

}
