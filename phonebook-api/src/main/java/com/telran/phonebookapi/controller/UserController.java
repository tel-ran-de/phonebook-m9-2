package com.telran.phonebookapi.controller;

import com.telran.phonebookapi.dto.NewPasswordDto;
import com.telran.phonebookapi.dto.RecoveryPasswordDto;
import com.telran.phonebookapi.dto.UserDto;
import com.telran.phonebookapi.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@CrossOrigin
@RequestMapping("/api/user")
public class UserController {

    UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("")
    public void addUser(@Valid @RequestBody UserDto userDto) {
        userService.addUser(userDto);
    }

    @GetMapping("/activation/{token}")
    public void activateUser(@PathVariable String token) {
        userService.activateUser(token);
    }

    @PostMapping("/password/recovery")
    public void recoverPassword(@Valid @RequestBody RecoveryPasswordDto recoveryPasswordDto) {
        userService.sendRecoveryToken(recoveryPasswordDto.email);
    }

    @PutMapping("/password")
    public void changePassword(@Valid @RequestBody NewPasswordDto newPasswordDto) {
        userService.createNewPassword(newPasswordDto.token, newPasswordDto.password);
    }
}

