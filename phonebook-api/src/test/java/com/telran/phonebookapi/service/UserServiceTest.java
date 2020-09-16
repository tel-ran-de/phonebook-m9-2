package com.telran.phonebookapi.service;

import com.telran.phonebookapi.dto.ContactDto;
import com.telran.phonebookapi.dto.UserDto;
import com.telran.phonebookapi.model.Contact;
import com.telran.phonebookapi.model.RecoveryToken;
import com.telran.phonebookapi.model.User;
import com.telran.phonebookapi.persistance.IActivationTokenRepository;
import com.telran.phonebookapi.persistance.IContactRepository;
import com.telran.phonebookapi.persistance.IRecoveryTokenRepository;
import com.telran.phonebookapi.persistance.IUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
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

    @Mock
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Mock
    IContactRepository contactRepository;

    @InjectMocks
    UserService userService;

    @BeforeEach
    public void init() {
        lenient().doAnswer(invocation -> invocation.getArgument(0)).when(bCryptPasswordEncoder).encode(anyString());
    }

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
        User newUser = new User("ivanov@gmail.com", "12345678");
        userService.addUser("ivanov@gmail.com", "12345678");

        verify(userRepository, times(1)).save(any());
        verify(userRepository, times(1)).save(argThat(user ->
                user.getEmail().equals(newUser.getEmail())
                        && user.getPassword().equals(newUser.getPassword())
        ));
        verify(activationTokenRepository, times(1)).save(any());
        verify(activationTokenRepository, times(1)).save(argThat(token ->
                token.getUser().getEmail().equals(newUser.getEmail())
        ));
        verify(emailSender, times(1)).sendMail(eq(newUser.getEmail()),
                eq(UserService.ACTIVATION_SUBJECT),
                anyString());
    }

    @Test
    public void testGetByEmail_userExist_User() {
        String email = "johndoe@mail.com";
        User ourUser = new User(email, "1234");

        when(userRepository.findById(email)).thenReturn(Optional.of(ourUser));

        User userFounded = userService.getUserByEmail(ourUser.getEmail());

        assertEquals(ourUser.getEmail(), userFounded.getEmail());

        verify(userRepository, times(1)).findById(argThat(
                id -> id.equals(ourUser.getEmail())));
    }
}

