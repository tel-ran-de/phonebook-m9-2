package com.telran.phonebookapi.service;

import com.telran.phonebookapi.dto.EmailDto;
import com.telran.phonebookapi.mapper.EmailMapper;
import com.telran.phonebookapi.model.Contact;
import com.telran.phonebookapi.model.Email;
import com.telran.phonebookapi.model.User;
import com.telran.phonebookapi.persistance.IContactRepository;
import com.telran.phonebookapi.persistance.IEmailRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class EmailServiceTest {
    @Mock
    IContactRepository contactRepository;

    @Mock
    IEmailRepository emailRepository;

    @InjectMocks
    EmailService emailService;

    @Spy
    EmailMapper emailMapper;

    @Captor
    ArgumentCaptor<Email> emailCaptor;

    @Test
    public void testAdd_contactExists_contactWithEmailNumber() {
        User user = new User("test@gmail.com", "test");
        Contact contact = new Contact("Name", user);

        when(contactRepository.findById(contact.getId())).thenReturn(Optional.of(contact));

        Email email = new Email("mail@mail.com", contact);
        emailService.add(email.getEmail(), contact.getId());

        verify(emailRepository, times(1)).save(any());
        verify(emailRepository).save(emailCaptor.capture());

        List<Email> capturedEmails = emailCaptor.getAllValues();
        assertEquals(1, capturedEmails.size());
        assertEquals(email.getEmail(), capturedEmails.get(0).getEmail());
    }

//    @Test
//    public void testAdd_contactDoesNotExist_EntityNotFoundException() {
//
//        EmailDto emailDto = new EmailDto();
//        emailDto.contactId = 0;
//        emailDto.email = "mail@mail.com";
//
//        Exception exception = assertThrows(EntityNotFoundException.class, ()->emailService.add(emailDto));
//
//        verify(contactRepository, times(1)).findById(any());
//        assertEquals("Error! This contact doesn't exist in our DB", exception.getMessage());
//    }

    @Test
    public void testEditEmail_emailExist_EmailChanged() {

        User user = new User("test@gmail.com", "test");
        Contact contact = new Contact("TestName", user);
        when(contactRepository.findById(contact.getId())).thenReturn(Optional.of(contact));

        Email email = new Email("mail@mail.com", contact);
        emailService.add(email.getEmail(), contact.getId());
        when(emailRepository.findById(email.getId())).thenReturn(Optional.of(email));

        emailService.edit("newmail@mail.com",email.getId());

        verify(emailRepository, times(2)).save(any());

        Email emailFounded = emailService.getById(email.getId());
        assertEquals(email.getEmail(), emailFounded.getEmail());
        assertEquals(contact.getId(), emailFounded.getContact().getId());
        assertEquals(email.getId(), emailFounded.getId());

        verify(emailRepository, times(2)).findById(argThat(
                id -> id == email.getId()));
    }

//    @Test
//    public void testEditAny_emailDoesNotExist_EntityNotFoundException() {
//
//        EmailDto emailDto = new EmailDto();
//
//        Exception exception = assertThrows(EntityNotFoundException.class, () -> emailService.edit(emailDto));
//
//        verify(emailRepository, times(1)).findById(any());
//        assertEquals("Error! This email doesn't exist in our DB", exception.getMessage());
//    }

    @Captor
    ArgumentCaptor<Email> emailArgumentCaptor;

    @Test
    public void testRemoveById_emailExists_EmailDeleted() {

        User user = new User("test@gmail.com", "test");
        Contact contact = new Contact("TestName", user);
        Email email = new Email("mail@mail.com", contact);

        EmailDto emailDto = new EmailDto();
        emailDto.id = 0;

        when(emailRepository.findById(emailDto.id)).thenReturn(Optional.of(email));

        emailService.removeById(emailDto.id);

        List<Email> capturedAddresses = emailArgumentCaptor.getAllValues();
        verify(emailRepository, times(1)).deleteById(emailDto.id);
        assertEquals(0, capturedAddresses.size());
    }

    @Test
    public void testGetById_contactWithEmail_EmailNumber() {

        User user = new User("test@gmail.com", "test");
        Contact contact = new Contact("TestName", user);
        Email email = new Email("mail@mail.com", contact);

        EmailDto emailDto = new EmailDto(0, "mail@mail.com",0);

        when(emailRepository.findById(emailDto.id)).thenReturn(Optional.of(email));
        Email emailFounded = emailService.getById(emailDto.id);

        assertEquals(emailDto.email, emailFounded.getEmail());

        verify(emailRepository, times(1)).findById(argThat(
                id -> id == emailDto.id));
    }
}
