package com.telran.phonebookapi.service;

import com.telran.phonebookapi.dto.UserDto;
import com.telran.phonebookapi.exception.TokenNotFoundException;
import com.telran.phonebookapi.exception.UserAlreadyExistsException;
import com.telran.phonebookapi.mapper.UserMapper;
import com.telran.phonebookapi.model.*;
import com.telran.phonebookapi.persistance.IActivationTokenRepository;
import com.telran.phonebookapi.persistance.IContactRepository;
import com.telran.phonebookapi.persistance.IRecoveryTokenRepository;
import com.telran.phonebookapi.persistance.IUserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.UUID;

@Service
public class UserService {

    static final String USER_ALREADY_EXISTS = "Error! User already exists";
    static final String ACTIVATION_SUBJECT = "User activation";
    static final String ACTIVATION_MESSAGE = "Please, follow the link to activate your account: ";
    static final String NOT_ACTIVE_LINK = "Your link is not active anymore";
    static final String USER_DOES_NOT_EXIST = "Error! This user doesn't exist";
    static final String RECOVER_YOUR_PASSWORD_MESSAGE = "Please click the link to recover your password ";
    static final String UI_RECOVERY_LINK = "/user/password-recovery/";
    static final String UI_ACTIVATION_LINK = "/user/activation/";

    @Value("${com.telran.phonebook.ui.host}")
    private String uiHost;

    private final IUserRepository userRepository;
    private final IContactRepository contactRepository;
    private final IActivationTokenRepository activationTokenRepository;
    private final IRecoveryTokenRepository recoveryTokenRepository;
    private final EmailSender emailSender;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    UserMapper userMapper;

    public UserService(IUserRepository userRepository,
                       IContactRepository contactRepository,
                       IActivationTokenRepository activationTokenRepository,
                       EmailSender emailSender,
                       IRecoveryTokenRepository recoveryTokenRepository,
                       BCryptPasswordEncoder bCryptPasswordEncoder,
                       UserMapper userMapper) {
        this.userRepository = userRepository;
        this.contactRepository = contactRepository;
        this.activationTokenRepository = activationTokenRepository;
        this.emailSender = emailSender;
        this.recoveryTokenRepository = recoveryTokenRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userMapper = userMapper;
    }

    public void addUser(String email, String password) {

        if (userRepository.findById(email).isPresent()) {
            throw new UserAlreadyExistsException(USER_ALREADY_EXISTS);
        } else {
            String token = UUID.randomUUID().toString();
            String encodedPassword = bCryptPasswordEncoder.encode(password);
            User newUser = new User(email, encodedPassword);
            newUser.addRole(UserRole.USER);
            Contact profile = new Contact();
            contactRepository.save(profile);

            userRepository.save(user);
            profile.setUser(user);
            contactRepository.save(profile);


            activationTokenRepository.save(new ActivationToken(token, newUser));
            emailSender.sendMail(newUser.getEmail(), ACTIVATION_SUBJECT, ACTIVATION_MESSAGE
                    + uiHost + UI_ACTIVATION_LINK + token);
        }
    }

    public void activateUser(String token) {
        ActivationToken activationToken = activationTokenRepository.findById(token).
                orElseThrow(() -> new TokenNotFoundException(NOT_ACTIVE_LINK));
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

        String message = RECOVER_YOUR_PASSWORD_MESSAGE + uiHost + UI_RECOVERY_LINK + token;

        emailSender.sendMail(email, "Password recovery", message);
    }

    public void createNewPassword(String recoveryToken, String password) {
        RecoveryToken token = recoveryTokenRepository.findById(recoveryToken).orElseThrow(() -> new TokenNotFoundException(NOT_ACTIVE_LINK));
        User ourUser = token.getUser();
        final String encryptedPassword = bCryptPasswordEncoder.encode(password);

        ourUser.setPassword(encryptedPassword);
        userRepository.save(ourUser);
        recoveryTokenRepository.delete(token);
    }

    public User getUserByEmail(String email) {
        return userRepository.findById(email).orElseThrow(() -> new EntityNotFoundException(USER_DOES_NOT_EXIST));
    }

    public void changePasswordAuthorizedUser(String email, String password) {
        User user = userRepository.findById(email).orElseThrow(() -> new EntityNotFoundException(USER_DOES_NOT_EXIST));
        final String encryptedPassword = bCryptPasswordEncoder.encode(password);
        user.setPassword(encryptedPassword);
        userRepository.save(user);
    }
}
