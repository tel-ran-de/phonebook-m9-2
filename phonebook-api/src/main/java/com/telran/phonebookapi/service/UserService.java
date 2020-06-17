package com.telran.phonebookapi.service;

import com.telran.phonebookapi.dto.UserDto;
import com.telran.phonebookapi.model.Token;
import com.telran.phonebookapi.model.User;
import com.telran.phonebookapi.persistance.ITokenRepository;
import com.telran.phonebookapi.persistance.IUserRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import java.util.UUID;

@Service
public class UserService {

    static final String USER_ALREADY_EXISTS = "Error! User already exists";

    final IUserRepository userRepository;
    final ITokenRepository tokenRepository;


    public UserService(IUserRepository userRepository, ITokenRepository tokenRepository) {
        this.userRepository = userRepository;
        this.tokenRepository = tokenRepository;
    }

    public String add(UserDto userDto) {
        if (userRepository.findById(userDto.email).isPresent()) {
            throw new EntityExistsException(USER_ALREADY_EXISTS);
        } else {
            String token = UUID.randomUUID().toString();
            User user = new User(userDto.email, userDto.password);
            user.setActive(false);
            userRepository.save(user);
            tokenRepository.save(new Token(token, user.getEmail()));
            return token;
        }
    }

    public void activate(Token tokenSaved, User user, Token tokenGet){
        if(tokenGet.equals(tokenSaved)){
            user.setActive(true);
        }
    }

}
