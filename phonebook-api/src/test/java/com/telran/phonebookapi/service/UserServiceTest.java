package com.telran.phonebookapi.service;

import com.telran.phonebookapi.dto.UserDto;
import com.telran.phonebookapi.persistance.IUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;

class UserServiceTest {

    IUserRepository userRepository;
    UserService userService;

    @BeforeEach
    public void init() {
        userRepository = mock(IUserRepository.class);

        userService = new UserService(
                userRepository);
    }

    @Test
    public void testAdd_user_passesToRepo() {

        UserDto userDto = new UserDto("ivanov@gmail.com", "12345");

        userService.add(userDto);

        verify(userRepository, times(1)).save(any());
        verify(userRepository, times(1)).save(argThat(user ->
                user.getEmail().equals(userDto.email)
                        && user.getPassword().equals(userDto.password)
        ));
    }
}
