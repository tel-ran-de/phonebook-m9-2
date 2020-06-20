package com.telran.phonebookapi.service;

import com.telran.phonebookapi.dto.UserDto;
import com.telran.phonebookapi.model.ActivationToken;
import com.telran.phonebookapi.model.User;
import com.telran.phonebookapi.persistance.IActivationTokenRepository;
import com.telran.phonebookapi.persistance.IUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;

class UserServiceTest {

    IUserRepository userRepository;
    UserService userService;
    IActivationTokenRepository activationTokenRepository;
    EmailSender emailSender;


    @BeforeEach
    public void init() {
        userRepository = mock(IUserRepository.class);
        activationTokenRepository = mock(IActivationTokenRepository.class);
        emailSender = mock(EmailSender.class);

        userService = new UserService(
                userRepository, activationTokenRepository, emailSender);
    }


    @Test
    public void testAdd_user_passesToRepo() {

        UserDto userDto = new UserDto("ivanov@gmail.com", "12345");

        userService.addUser(userDto);

        verify(userRepository, times(1)).save(any());
        verify(userRepository, times(1)).save(argThat(user ->
                user.getEmail().equals(userDto.email)
                        && user.getPassword().equals(userDto.password)
        ));
        verify(activationTokenRepository, times(1)).save(any());
        verify(activationTokenRepository, times(1)).save(argThat(token ->
                token.getUser().getEmail().equals(userDto.email)
        ));
    }
}

