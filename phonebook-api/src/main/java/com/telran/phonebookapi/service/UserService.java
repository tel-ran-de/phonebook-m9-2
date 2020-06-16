package com.telran.phonebookapi.service;

import com.telran.phonebookapi.dto.UserDto;
import com.telran.phonebookapi.model.User;
import com.telran.phonebookapi.persistance.IUserRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;

@Service
public class UserService {

    static final String USER_ALREADY_EXISTS = "Error! User already exists";

    final IUserRepository userRepository;


    public UserService(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void add(UserDto userDto) {
        if(userRepository.findById(userDto.email).isPresent()) {
            throw new EntityExistsException(USER_ALREADY_EXISTS);
        }
         else{
            userRepository.save(new User(userDto.email, userDto.password));
        }
    }
}
