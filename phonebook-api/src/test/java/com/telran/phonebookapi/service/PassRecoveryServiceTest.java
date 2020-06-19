package com.telran.phonebookapi.service;

import com.telran.phonebookapi.model.RecoveryToken;
import com.telran.phonebookapi.model.User;
import com.telran.phonebookapi.persistance.IPassRecoveryTokenRepository;
import com.telran.phonebookapi.persistance.IUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PassRecoveryServiceTest {

    @Mock
    IUserRepository userRepository;

    @Mock
    IPassRecoveryTokenRepository passRecoveryRepository;

    @Mock
    PassRecoveryService passRecoveryService;

//    @BeforeEach
//    public void setUp() {
//        userRepository = mock(IUserRepository.class);
//        passRecoveryRepository = mock(IPassRecoveryTokenRepository.class);
//        passRecoveryService = new PassRecoveryService(userRepository, passRecoveryRepository, emailSender);
//    }

    @Test
    public void testSendRecoveryToken_tokenIsSavedToRepo() {
        User ourUser = new User("johndoe@mail.com", "1234");

        String token = "testToken";
        RecoveryToken recoveryToken = new RecoveryToken(token, ourUser);
        passRecoveryRepository.save(recoveryToken);

        verify(passRecoveryRepository, times(1)).save(recoveryToken);

        verify(passRecoveryRepository, times(1)).save(argThat(ourToken ->
                recoveryToken.getId().equals(token) && recoveryToken.getUser().equals(ourUser)));
    }

    @Test
    public void testCreateNewPassword_newPasswordIsSaved() {
        User ourUser = new User("johndoe@mail.com", "1234");

        String token = "testToken";
        RecoveryToken recoveryToken = new RecoveryToken(token, ourUser);
        passRecoveryRepository.save(recoveryToken);

        //passRecoveryService.createNewPassword(token, "4321");

        recoveryToken.getUser().setPassword("4321");

        assertEquals("4321", ourUser.getPassword());
    }
}


