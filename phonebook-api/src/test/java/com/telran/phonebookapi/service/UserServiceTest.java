package com.telran.phonebookapi.service;

import com.telran.phonebookapi.dto.UserDto;
import com.telran.phonebookapi.model.RecoveryToken;
import com.telran.phonebookapi.model.User;
import com.telran.phonebookapi.persistance.IActivationTokenRepository;
import com.telran.phonebookapi.persistance.IRecoveryTokenRepository;
import com.telran.phonebookapi.persistance.IUserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    IUserRepository userRepository;

    @Mock
    IRecoveryTokenRepository recoveryTokenRepository;

    @Mock
    IActivationTokenRepository activationTokenRepository;

    @Mock
    EmailSender emailSender;

    @InjectMocks
    UserService userService;

    @Test
    public void testSendRecoveryToken_tokenIsSavedToRepo() {
        String email = "johndoe@mail.com";
        User ourUser = new User(email, "1234");

        when(userRepository.findById(email)).thenReturn(Optional.of(ourUser));

        userService.sendRecoveryToken(email);

        verify(recoveryTokenRepository, times(1)).save(any());

        verify(recoveryTokenRepository, times(1)).save(argThat(token ->
                token.getUser().getEmail().equals(email)));

        verify(emailSender, times(1)).sendMail(eq(email), anyString(), anyString());
    }

    @Test
    public void testCreateNewPassword_newPasswordIsSaved() {
        User ourUser = new User("johndoe@mail.com", "1234");
        String token = UUID.randomUUID().toString();
        RecoveryToken recoveryToken = new RecoveryToken(token, ourUser);

        when(recoveryTokenRepository.findById(token)).thenReturn(Optional.of(recoveryToken));

        userService.createNewPassword(token, "4321");

        verify(userRepository, times(1)).save(any());

        verify(userRepository, times(1)).save(argThat(user ->
                user.getPassword().equals("4321")));

        verify(recoveryTokenRepository, times(1)).findById(token);
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
        verify(emailSender, times(1)).sendMail(eq(userDto.email),
                eq(UserService.ACTIVATION_SUBJECT),
                anyString());
    }

}

