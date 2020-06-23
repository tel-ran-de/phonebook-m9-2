package com.telran.phonebookapi.service;

import com.telran.phonebookapi.model.RecoveryToken;
import com.telran.phonebookapi.model.User;
import com.telran.phonebookapi.persistance.IRecoveryTokenRepository;
import com.telran.phonebookapi.persistance.IUserRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.UUID;

@Service
public class UserService {

    final IUserRepository userRepository;
    final IRecoveryTokenRepository recoveryTokenRepository;

    static final String USER_DOES_NOT_EXIST = "Error! This user doesn't exist in our DB";
    static final String INCORRECT_TOKEN = "Error! Registration failed: Invalid confirmation token";
    static final String TEXT = "Please click the link to recover your password ";
    static final String RECOVERY_LINK = "/user/password-recovery/";

    private EmailSender emailSender;

    public UserService(IUserRepository userRepository, IRecoveryTokenRepository recoveryTokenRepository, EmailSender emailSender) {
        this.userRepository = userRepository;
        this.recoveryTokenRepository = recoveryTokenRepository;
        this.emailSender = emailSender;
    }

    public void sendRecoveryToken (String email){
        User ourUser = userRepository.findById(email).orElseThrow(() -> new EntityNotFoundException(USER_DOES_NOT_EXIST));
        String token = UUID.randomUUID().toString();
        RecoveryToken recoveryToken = new RecoveryToken(token, ourUser);
        recoveryTokenRepository.save(recoveryToken);

        String message = TEXT + RECOVERY_LINK + "{recoveryToken}";

        emailSender.sendMail(email, "Password recovery", message);
    }

    public void createNewPassword (String recoveryToken, String password){
        RecoveryToken token = recoveryTokenRepository.findById(recoveryToken).orElseThrow(() -> new EntityNotFoundException(INCORRECT_TOKEN));
        User ourUser = token.getUser();
        ourUser.setPassword(password);
        userRepository.save(ourUser);
    }
}
