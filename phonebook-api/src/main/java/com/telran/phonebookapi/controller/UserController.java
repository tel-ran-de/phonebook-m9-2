package com.telran.phonebookapi.controller;

import com.telran.phonebookapi.dto.NewPasswordDto;
import com.telran.phonebookapi.dto.RecoveryPasswordDto;
import com.telran.phonebookapi.dto.UserDto;
import com.telran.phonebookapi.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/user")
public class UserController {

    UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("")
    public void addUser(@Valid @RequestBody UserDto userDto) {
        userService.addUser(userDto.email, userDto.password);
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

    @GetMapping("")
    @PreAuthorize("isAuthenticated()")
    public UserDto getUser(Authentication auth) {
        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        return UserDto.builder()
                .roles(userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .email(userDetails.getUsername())
                .build();
    }

}

