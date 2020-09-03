package com.telran.phonebookapi.service;

import com.telran.phonebookapi.dto.UserDto;
import com.telran.phonebookapi.model.RecoveryToken;
import com.telran.phonebookapi.model.User;
import com.telran.phonebookapi.persistance.IActivationTokenRepository;
import com.telran.phonebookapi.persistance.IContactRepository;
import com.telran.phonebookapi.persistance.IRecoveryTokenRepository;
import com.telran.phonebookapi.persistance.IUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    IUserRepository userRepository;

    @Mock
    IRecoveryTokenRepository recoveryTokenRepository;

    @Mock
    IActivationTokenRepository activationTokenRepository;

    @Mock
    EmailSender emailSender;

    @Mock
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Mock
    IContactRepository contactRepository;

    @InjectMocks
    UserService userService;

    @BeforeEach
    public void init() {
        lenient().doAnswer(invocation -> invocation.getArgument(0)).when(bCryptPasswordEncoder).encode(anyString());
    }

    @Test
    public void testSendRecoveryToken_tokenIsSavedToRepo() {
        String email = "johndoe@mail.com";
        User ourUser = new User(email, "1234");

        when(userRepository.findById(email)).thenReturn(Optional.of(ourUser));

        userService.sendRecoveryToken(email);

        verify(recoveryTokenRepository, times(1)).save(any());

        verify(recoveryTokenRepository, times(1)).save(argThat(token ->
                token.getUser().getEmail().equals(email)));

        verify(emailSender, times(1)).sendMail(eq(email), anyString(), anyString());
    }

    @Test
    public void testCreateNewPassword_newPasswordIsSaved() {
        User ourUser = new User("johndoe@mail.com", "1234");
        String token = UUID.randomUUID().toString();
        RecoveryToken recoveryToken = new RecoveryToken(token, ourUser);

        when(recoveryTokenRepository.findById(token)).thenReturn(Optional.of(recoveryToken));

        userService.createNewPassword(token, "4321");

        verify(userRepository, times(1)).save(any());

        verify(userRepository, times(1)).save(argThat(user ->
                user.getPassword().equals("4321")));

        verify(recoveryTokenRepository, times(1)).findById(token);
    }

    @Test
    public void testAdd_user_passesToRepo() {

        UserDto userDto = UserDto.builder()
                .email("ivanov@gmail.com")
                .password("pass")
                .build();

        userService.addUser(userDto);

        verify(userRepository, times(1)).save(any());
        verify(userRepository, times(1)).save(argThat(user ->
                user.getEmail().equals(userDto.email)
                        && user.getPassword().equals(userDto.password)
        ));
        verify(activationTokenRepository, times(1)).save(any());
        verify(activationTokenRepository, times(1)).save(argThat(token ->
                token.getUser().getEmail().equals(userDto.email)
        ));
        verify(emailSender, times(1)).sendMail(eq(userDto.email),
                eq(UserService.ACTIVATION_SUBJECT),
                anyString());
    }

//    @Test
//    public void testEditAllFields_userExist_AllFieldsChanged() {
//
//        User oldUser = new User("test@gmail.com", "12345678");
//        Contact oldProfile = new Contact();
//        oldProfile.setFirstName("Name");
//        oldProfile.setLastName("LastName");
//        oldUser.setMyProfile(oldProfile);
//
//        UserDto userDto = UserDto.builder()
//                .email("test@gmail.com")
//                .password("12345678")
//                .build();
//
//
//        ContactDto profileDto = new ContactDto();
//        profileDto.firstName = "NewName";
//        profileDto.lastName = "NewLastName";
//        userDto.myProfile = profileDto;
//
//        when(userRepository.findById(userDto.email)).thenReturn(Optional.of(oldUser));
//
//        userService.editAllFields(userDto);
//
//        verify(userRepository, times(1)).save(any());
//
//        verify(userRepository, times(1)).save(argThat(user ->
//                user.getEmail().equals(userDto.email)
//                        && user.getMyProfile().getFirstName().equals(userDto.myProfile.firstName) && user.getMyProfile().getLastName().equals(userDto.myProfile.lastName
//                )));
//    }

//    @Test
//    public void testEditAny_userDoesNotExist_EntityNotFoundException() {
//
//        UserDto userDto = new UserDto("test@gmail.com", "12345678");
//
//        Exception exception = assertThrows(EntityNotFoundException.class, () -> userService.editAllFields(userDto));
//
//        verify(userRepository, times(1)).findById(any());
//        assertEquals("Error! This user doesn't exist in our DB", exception.getMessage());
//    }

    @Captor
    ArgumentCaptor<User> userCaptor;

//    @Test
//    public void testRemoveById_userExists_UserDeleted() {
//
//        User user = new User("test@gmail.com", "12345678");
//
//        UserDto userDto = UserDto.builder()
//                .email(user.getEmail())
//                .password(user.getPassword())
//                .build();
//
//        when(userRepository.findById(userDto.email)).thenReturn(Optional.of(user));
//        userService.removeById(userDto.email);
//
//        List<User> capturedUsers = userCaptor.getAllValues();
//        verify(userRepository, times(1)).deleteById(userDto.email);
//        assertEquals(0, capturedUsers.size());
//    }

}

