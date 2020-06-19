package com.telran.phonebookapi.controller;

import com.telran.phonebookapi.dto.UserDto;
import com.telran.phonebookapi.service.UserService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Validated
public class UserController {

    UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/api/user")
    public void addUser(@RequestBody UserDto userDto) {
        userService.addUser(userDto);
    }

    @GetMapping("/api/user/activation/{token}")
    public void activateUser(@PathVariable String token) {
        userService.activateUser(token);
    }
}
