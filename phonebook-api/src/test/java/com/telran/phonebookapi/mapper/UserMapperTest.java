package com.telran.phonebookapi.mapper;

import com.telran.phonebookapi.dto.UserDto;
import com.telran.phonebookapi.model.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserMapperTest {

    UserMapper userMapper = new UserMapper();

    @Test
    void UserDTO() {
        User user = new User("test@gmail.com", "112233");

        UserDto userDto = new UserDto("test@gmail.com", "112233");

        UserDto userDtoMapped = userMapper.mapUserToDto(user);
        assertEquals(userDto.email, userDtoMapped.email);
        assertEquals(userDto.password, userDtoMapped.password);
    }
}
