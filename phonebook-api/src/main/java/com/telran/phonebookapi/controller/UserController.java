package com.telran.phonebookapi.controller;

import com.telran.phonebookapi.dto.NewPasswordDto;
import com.telran.phonebookapi.dto.RecoveryPasswordDto;
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

    @CrossOrigin
    @PostMapping("/api/user")
    public void addUser(@RequestBody UserDto userDto) {
        userService.addUser(userDto);
    }

    @CrossOrigin
    @GetMapping("/api/user/activation/{token}")
    public void activateUser(@PathVariable String token) {
        userService.activateUser(token);
    }

    @CrossOrigin
    @PostMapping("/api/user/password/recovery")
    public void recoverPassword(RecoveryPasswordDto recoveryPasswordDto) {
        userService.sendRecoveryToken(recoveryPasswordDto.email);
    }

    @CrossOrigin
    @PutMapping("/api/user/password")
    public void changePassword(NewPasswordDto newPasswordDto) {
        userService.createNewPassword(newPasswordDto.token, newPasswordDto.password);
    }
}

