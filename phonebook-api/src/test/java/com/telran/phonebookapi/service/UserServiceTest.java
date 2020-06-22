package com.telran.phonebookapi.service;

import com.telran.phonebookapi.model.RecoveryToken;
import com.telran.phonebookapi.model.User;
import com.telran.phonebookapi.persistance.IRecoveryTokenRepository;
import com.telran.phonebookapi.persistance.IUserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    IUserRepository userRepository;

    @Mock
    IRecoveryTokenRepository recoveryTokenRepository;

    @Mock
    EmailSender emailSender;

    @InjectMocks
    UserService userService;

//    @BeforeEach
//    public void setUp() {
//        userRepository = mock(IUserRepository.class);
//        passRecoveryRepository = mock(IPassRecoveryTokenRepository.class);
//        passRecoveryService = new PassRecoveryService(userRepository, passRecoveryRepository, emailSender);
//    }

    @Test
    public void testSendRecoveryToken_tokenIsSavedToRepo() {

        User ourUser = new User("johndoe@mail.com", "1234");
        RecoveryToken recoveryToken = new RecoveryToken("token", ourUser);
        recoveryTokenRepository.save(recoveryToken);

        userService.sendRecoveryToken("johndoe@mail.com");

        verify(recoveryTokenRepository, times(1)).save(any());

        verify(recoveryTokenRepository, times(1)).save(argThat(token ->
                token.getId().equals("token") && token.getUser().equals(ourUser)));

        verify(emailSender, times(1)).sendMail(eq(ourUser.getEmail()), anyString(), anyString());

    }

    @Test
    public void testCreateNewPassword_newPasswordIsSaved() {
        User ourUser = new User("johndoe@mail.com", "1234");

//        String token = "testToken";
//        RecoveryToken recoveryToken = new RecoveryToken(token, ourUser);
//        recoveryTokenRepository.save(recoveryToken);

        userService.createNewPassword("token", "4321");

        verify(userService, times (1)).createNewPassword("token", "4321");

        verify(recoveryTokenRepository, times(1)).save(argThat(recoveryToken ->
                recoveryToken.getId().equals("token") && recoveryToken.getUser().getPassword().equals(ourUser.getPassword())));
    }
}


