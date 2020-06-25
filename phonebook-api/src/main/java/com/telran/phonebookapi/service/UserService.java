package com.telran.phonebookapi.service;

import com.telran.phonebookapi.dto.UserDto;
import com.telran.phonebookapi.model.ActivationToken;
import com.telran.phonebookapi.model.RecoveryToken;
import com.telran.phonebookapi.model.User;
import com.telran.phonebookapi.persistance.IActivationTokenRepository;
import com.telran.phonebookapi.persistance.IRecoveryTokenRepository;
import com.telran.phonebookapi.persistance.IUserRepository;
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
    static final String USER_DOES_NOT_EXIST = "Error! This user doesn't exist in our DB";
    static final String INCORRECT_TOKEN = "Error! Registration failed: Invalid confirmation token";
    static final String TEXT = "Please click the link to recover your password ";
    static final String UI_RECOVERY_LINK = "/user/password-recovery/";
    static final String UI_ACTIVATION_LINK = "/user/activation/";

    @Value("${com.telran.phonebook.ui.host}")
    String uiHost;

    final IUserRepository userRepository;
    final IActivationTokenRepository activationTokenRepository;
    final IRecoveryTokenRepository recoveryTokenRepository;
    final EmailSender emailSender;


    public UserService(IUserRepository userRepository,
                       IActivationTokenRepository activationTokenRepository,
                       EmailSender emailSender,
                       IRecoveryTokenRepository recoveryTokenRepository) {
        this.userRepository = userRepository;
        this.activationTokenRepository = activationTokenRepository;
        this.emailSender = emailSender;
        this.recoveryTokenRepository = recoveryTokenRepository;
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
                    + uiHost + UI_ACTIVATION_LINK + token);
        }
    }

    public void activateUser(String token) {
        ActivationToken activationToken = activationTokenRepository.findById(token).
                orElseThrow(() -> new EntityNotFoundException(NOT_ACTIVE_LINK));
        User user = activationToken.getUser();
        user.setActive(true);
        userRepository.save(user);
        activationTokenRepository.delete(activationToken);
    }

    public void sendRecoveryToken(String email) {
        User ourUser = userRepository.findById(email).orElseThrow(() -> new EntityNotFoundException(USER_DOES_NOT_EXIST));
        String token = UUID.randomUUID().toString();
        RecoveryToken recoveryToken = new RecoveryToken(token, ourUser);
        recoveryTokenRepository.save(recoveryToken);

        String message = TEXT + uiHost + UI_RECOVERY_LINK + recoveryToken;

        emailSender.sendMail(email, "Password recovery", message);
    }

    public void createNewPassword(String recoveryToken, String password) {
        RecoveryToken token = recoveryTokenRepository.findById(recoveryToken).orElseThrow(() -> new EntityNotFoundException(INCORRECT_TOKEN));
        User ourUser = token.getUser();
        ourUser.setPassword(password);
        userRepository.save(ourUser);
        recoveryTokenRepository.delete(token);
    }

}
