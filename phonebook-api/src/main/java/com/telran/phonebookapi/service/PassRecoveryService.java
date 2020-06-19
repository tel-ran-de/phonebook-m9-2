package com.telran.phonebookapi.service;

import com.telran.phonebookapi.model.RecoveryToken;
import com.telran.phonebookapi.model.User;
import com.telran.phonebookapi.persistance.IPassRecoveryTokenRepository;
import com.telran.phonebookapi.persistance.IUserRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.UUID;

@Service
public class PassRecoveryService {

    final IUserRepository userRepository;
    final IPassRecoveryTokenRepository passRecoveryRepository;

    static final String USER_DOES_NOT_EXIST = "Error! This user doesn't exist in out DB";
    static final String INCORRECT_TOKEN = "Error! Registration failed: Invalid confirmation token";

    private EmailSender emailSender;

    public PassRecoveryService(IUserRepository userRepository, IPassRecoveryTokenRepository passRecoveryRepository, EmailSender emailSender) {
        this.userRepository = userRepository;
        this.passRecoveryRepository = passRecoveryRepository;
        this.emailSender = emailSender;
    }

    public void sendRecoveryToken (String email){
        User ourUser = userRepository.findById(email).orElseThrow(() -> new EntityNotFoundException(USER_DOES_NOT_EXIST));
        String token = UUID.randomUUID().toString();
        RecoveryToken recoveryToken = new RecoveryToken(token, ourUser);
        passRecoveryRepository.save(recoveryToken);

        String message = "Please click the link to recover your password " + "/user/password-recovery/{recoveryToken}";

        emailSender.sendMail(email, "Password recovery", message);
    }

    public void createNewPassword (String recoveryToken, String password){
        if (passRecoveryRepository.findById(recoveryToken).isPresent()){
            User ourUser = passRecoveryRepository.findById(recoveryToken).get().getUser();
            ourUser.setPassword(password);
            userRepository.save(ourUser);
        }else{
            throw new EntityNotFoundException(INCORRECT_TOKEN);
        }
    }
}
