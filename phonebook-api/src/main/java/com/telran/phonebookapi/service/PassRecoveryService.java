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

    private EmailSender emailSender;

    public PassRecoveryService(IUserRepository userRepository, IPassRecoveryTokenRepository passRecoveryRepository) {
        this.userRepository = userRepository;
        this.passRecoveryRepository = passRecoveryRepository;
    }

    public void createNewPassword (String email){
            User ourUser = userRepository.findById(email).orElseThrow(() -> new EntityNotFoundException(USER_DOES_NOT_EXIST));;
            String recoveryToken = UUID.randomUUID().toString();
            passRecoveryRepository.save(new RecoveryToken(recoveryToken, ourUser));
            emailSender.sendMail(email, "Password recovery", "Please click the link to recover your password " + recoveryToken);
    }
}
