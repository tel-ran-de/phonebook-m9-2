package com.telran.phonebookapi.mapper;

import com.telran.phonebookapi.dto.UserDto;
import com.telran.phonebookapi.model.User;
import com.telran.phonebookapi.model.UserRole;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class UserMapper {

    public UserDto mapUserToDto(User user) {
        return UserDto.builder()
                .email(user.getEmail())
                .roles(user.getRoles().stream().map(UserRole::getRole).collect(Collectors.toList()))
                .build();
    }
}
