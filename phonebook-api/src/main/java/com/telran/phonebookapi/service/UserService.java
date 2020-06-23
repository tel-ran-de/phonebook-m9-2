package com.telran.phonebookapi.service;

import com.telran.phonebookapi.dto.UserDto;
import com.telran.phonebookapi.model.ActivationToken;
import com.telran.phonebookapi.model.User;
import com.telran.phonebookapi.persistance.IActivationTokenRepository;
import com.telran.phonebookapi.persistance.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.UUID;

@Service
public class UserService {

    static final String USER_ALREADY_EXISTS = "Error! User already exists";
    static final String ACTIVATION_SUBJECT = "User activation";
    static final String ACTIVATION_MESSAGE = "Please, follow the link to activate your account: ";
    static final String NOT_ACTIVE_LINK = "Your link is not active anymore";


    @Value("{$angular.activation.path}")
    String activationPath;

    final IUserRepository userRepository;
    final IActivationTokenRepository activationTokenRepository;
    final EmailSender emailSender;


    public UserService(IUserRepository userRepository, IActivationTokenRepository activationTokenRepository, EmailSender emailSender) {
        this.userRepository = userRepository;
        this.activationTokenRepository = activationTokenRepository;
        this.emailSender = emailSender;
    }

    public void addUser(UserDto userDto) {
        if (userRepository.findById(userDto.email).isPresent()) {
            throw new EntityExistsException(USER_ALREADY_EXISTS);
        } else {
            String token = UUID.randomUUID().toString();
            User user = new User(userDto.email, userDto.password);
            user.setActive(false);
            userRepository.save(user);
            activationTokenRepository.save(new ActivationToken(token, user));
            emailSender.sendMail(user.getEmail(), ACTIVATION_SUBJECT, ACTIVATION_MESSAGE
                    + activationPath + token);
        }
    }

    public void activateUser(String token) {
        ActivationToken activationToken = activationTokenRepository.findById(token).
                orElseThrow(()->new EntityNotFoundException(NOT_ACTIVE_LINK));
        User user = activationToken.getUser();
        user.setActive(true);
        userRepository.save(user);
        }
    }

