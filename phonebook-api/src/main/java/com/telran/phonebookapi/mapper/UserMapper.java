package com.telran.phonebookapi.mapper;

import com.telran.phonebookapi.dto.UserDto;
import com.telran.phonebookapi.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserDto mapUserToDto(User user) {
        return new UserDto(user.getEmail(), user.getPassword());
    }
}
