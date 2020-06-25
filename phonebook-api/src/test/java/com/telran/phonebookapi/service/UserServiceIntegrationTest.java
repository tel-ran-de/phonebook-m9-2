package com.telran.phonebookapi.service;

import com.telran.phonebookapi.model.ActivationToken;
import com.telran.phonebookapi.model.User;
import com.telran.phonebookapi.persistance.IActivationTokenRepository;
import com.telran.phonebookapi.persistance.IUserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityNotFoundException;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class UserServiceIntegrationTest {

    @Autowired
    IActivationTokenRepository activationTokenRepository;
    @Autowired
    IUserRepository userRepository;
    @Autowired
    UserService userService;

    @Test
    public void testActivateUser_tokenExist() {
        User user = new User("ivanov@gmail.com", "12345");
        String token = UUID.randomUUID().toString();
        ActivationToken activationToken = new ActivationToken(token, user);
        user.setActive(false);

        assertEquals("ivanov@gmail.com", user.getEmail());
        assertEquals(token, activationToken.getUuid());
        assertEquals("ivanov@gmail.com", activationToken.getUser().getEmail());
        assertFalse(user.isActive());

        userRepository.save(user);
        activationTokenRepository.save(activationToken);

        userService.activateUser(token);
        assertTrue(userRepository.findById(user.getEmail()).get().isActive());
    }

    @Test()
    public void testActivateUser_tokenNotExist() {
        User user = new User("ivanov@gmail.com", "12345");
        String token = UUID.randomUUID().toString();
        ActivationToken activationToken = new ActivationToken(token, user);
        user.setActive(false);

        assertEquals("ivanov@gmail.com", user.getEmail());
        assertEquals(token, activationToken.getUuid());
        assertEquals("ivanov@gmail.com", activationToken.getUser().getEmail());
        assertFalse(user.isActive());

        userRepository.save(user);
        activationTokenRepository.save(activationToken);

        String tokenFalse = "111111111111111111";
        Exception exception = assertThrows(EntityNotFoundException.class, () -> userService.activateUser(tokenFalse));
        assertTrue(exception.getMessage().contains(UserService.NOT_ACTIVE_LINK));
        assertFalse(userRepository.findById(user.getEmail()).get().isActive());
    }
}
