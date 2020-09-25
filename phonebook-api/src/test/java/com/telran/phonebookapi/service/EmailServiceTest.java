package com.telran.phonebookapi.service;

import com.telran.phonebookapi.model.Contact;
import com.telran.phonebookapi.model.Email;
import com.telran.phonebookapi.model.User;
import com.telran.phonebookapi.persistance.IContactRepository;
import com.telran.phonebookapi.persistance.IEmailRepository;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@TestMethodOrder(MethodOrderer.Alphanumeric.class)
@ExtendWith(MockitoExtension.class)
public class EmailServiceTest {
    @Mock
    IContactRepository contactRepository;

    @Mock
    IEmailRepository emailRepository;

    @InjectMocks
    EmailService emailService;

    @InjectMocks
    List<Email> emails = new ArrayList<>();

    @Captor
    ArgumentCaptor<Email> emailCaptor;

    String error_EmailDoesNotExist = "Error! This email doesn't exist";
    String error_ContactDoesNotExist = "Error! This contact doesn't exist";

    @Test
    public void testAdd_contactExists_contactWithEmailNumber() {
        User user = new User("test@gmail.com", "test");
        Contact contact = new Contact("Name", user);

        when(contactRepository.findById(contact.getId())).thenReturn(Optional.of(contact));

        Email email = new Email("mail@mail.com", contact);
        emailService.add(contact.getId(), email.getEmail());

        verify(emailRepository, times(1)).save(any());
        verify(emailRepository).save(emailCaptor.capture());

        List<Email> capturedEmails = emailCaptor.getAllValues();
        assertEquals(1, capturedEmails.size());
        assertEquals(email.getEmail(), capturedEmails.get(0).getEmail());
    }

    @Test
    public void testAdd_contactDoesNotExist_EntityNotFoundException() {
        Exception exception = assertThrows(EntityNotFoundException.class, ()
                -> emailService.add(1, "mail@mail.com"));

        assertEquals(error_ContactDoesNotExist, exception.getMessage());
    }

    @Test
    public void testEditEmail_emailExist_EmailChanged() {
        User user = new User("test@gmail.com", "test");
        Contact contact = new Contact("TestName", user);

        when(contactRepository.findById(contact.getId())).thenReturn(Optional.of(contact));

        Email email = new Email("mail@mail.com", contact);
        emailService.add(contact.getId(), email.getEmail());

        when(emailRepository.findById(email.getId())).thenReturn(Optional.of(email));

        emailService.edit(email.getId(), "newmail@mail.com");

        verify(emailRepository, times(2)).save(any());

        Email emailFounded = emailService.getById(email.getId());
        assertEquals(email.getEmail(), emailFounded.getEmail());
        assertEquals(contact.getId(), emailFounded.getContact().getId());
        assertEquals(email.getId(), emailFounded.getId());

        verify(emailRepository, times(2)).findById(argThat(
                id -> id == email.getId()));
    }

    @Test
    public void testEditEmail_emailDoesNotExist_EntityNotFoundException() {
        Exception exception = assertThrows(EntityNotFoundException.class, ()
                -> emailService.edit(1, "newmail@mail.com"));

        assertEquals(error_EmailDoesNotExist, exception.getMessage());
    }

    @Test
    public void testRemoveById_emailExists_EmailDeleted() {
        User user = new User("test@gmail.com", "test");
        Contact contact = new Contact("TestName", user);
        Email email = new Email("mail@mail.com", contact);

        when(emailRepository.findById(email.getId())).thenReturn(Optional.of(email));

        emailService.removeById(email.getId());

        List<Email> capturedEmails = emailCaptor.getAllValues();
        verify(emailRepository, times(1)).deleteById(email.getId());
        assertEquals(0, capturedEmails.size());
    }

    @Test
    public void testRemoveById_emailDoesNotExist_EntityNotFoundException() {
        Exception exception = assertThrows(EntityNotFoundException.class, ()
                -> emailService.removeById(1));

        assertEquals(error_EmailDoesNotExist, exception.getMessage());
    }

    @Test
    public void testGetById_oneEmail_EmailNumber() {

        User user = new User("test@gmail.com", "test");
        Contact contact = new Contact("TestName", user);
        Email email = new Email("mail@mail.com", contact);

        when(emailRepository.findById(email.getId())).thenReturn(Optional.of(email));
        Email emailFounded = emailService.getById(email.getId());

        assertEquals(email.getEmail(), emailFounded.getEmail());

        verify(emailRepository, times(1)).findById(argThat(
                id -> id == email.getId()));
    }

    @Test
    public void testGetById_threeEmails_oneEmailFounded() {

        User user = new User("test@gmail.com", "test");
        Contact contact = new Contact("TestName", user);

        when(contactRepository.findById(contact.getId())).thenReturn(Optional.of(contact));

        Email email = new Email("mail@mail.com", contact);
        Email email2 = new Email("mail2@mail.com", contact);
        Email email3 = new Email("mail3@mail.com", contact);
        emailService.add(contact.getId(), email.getEmail());
        emailService.add(contact.getId(), email2.getEmail());
        emailService.add(contact.getId(), email3.getEmail());

        when(emailRepository.findById(email2.getId())).thenReturn(Optional.of(email2));

        Email emailFounded = emailService.getById(email2.getId());

        assertEquals(email2.getEmail(), emailFounded.getEmail());
    }

    @Test
    public void testGetById_emailDoesNotExist_EntityNotFoundException() {
        Exception exception = assertThrows(EntityNotFoundException.class, ()
                -> emailService.getById(2));

        assertEquals(error_EmailDoesNotExist, exception.getMessage());
    }

}
