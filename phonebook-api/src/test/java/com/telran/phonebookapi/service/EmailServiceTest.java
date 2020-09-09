package com.telran.phonebookapi.service;

import com.telran.phonebookapi.dto.EmailDto;
import com.telran.phonebookapi.dto.PhoneDto;
import com.telran.phonebookapi.mapper.EmailMapper;
import com.telran.phonebookapi.mapper.PhoneMapper;
import com.telran.phonebookapi.model.Contact;
import com.telran.phonebookapi.model.Email;
import com.telran.phonebookapi.model.Phone;
import com.telran.phonebookapi.model.User;
import com.telran.phonebookapi.persistance.IContactRepository;
import com.telran.phonebookapi.persistance.IEmailRepository;
import com.telran.phonebookapi.persistance.IPhoneRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
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

    @Test
    public void testAdd_contactExists_contactWithEmailNumber() {
        User user = new User("test@gmail.com", "test");
        Contact contact = new Contact("Name", user);

        when(contactRepository.findById(contact.getId())).thenReturn(Optional.of(contact));

        EmailDto emailDto = new EmailDto();
        emailDto.contactId = 0;
        emailDto.email = "mail@mail.com";
        emailService.add(emailDto);

        verify(emailRepository, times(1)).save(any());
        verify(emailRepository,times(1)).save(argThat(email->email.getEmail().equals(emailDto.email)&&
                email.getContact().getId() == emailDto.contactId));

    }

    @Test
    public void testAdd_contactDoesNotExist_EntityNotFoundException() {

        EmailDto emailDto = new EmailDto();
        emailDto.contactId = 0;
        emailDto.email = "mail@mail.com";

        Exception exception = assertThrows(EntityNotFoundException.class, ()->emailService.add(emailDto));

        verify(contactRepository, times(1)).findById(any());
        assertEquals("Error! This contact doesn't exist in our DB", exception.getMessage());
    }

    @Test
    public void testEditAllFields_emailExist_AllFieldsChanged() {

        User user = new User("test@gmail.com", "test");

        Contact oldContact = new Contact("TestName", user);
        Email oldEmail = new Email(oldContact);

        EmailDto emailDto = new EmailDto();
        emailDto.id = 0;
        emailDto.email = "mail@mail.com";

        when(emailRepository.findById(emailDto.id)).thenReturn(Optional.of(oldEmail));

        emailService.edit(emailDto);

        verify(emailRepository, times(1)).save(any());
        verify(emailRepository, times(1)).save(argThat(email->
                email.getEmail().equals(emailDto.email)&& email.getContact().getId()==emailDto.contactId));

    }

    @Test
    public void testEditAny_emailDoesNotExist_EntityNotFoundException() {

        EmailDto emailDto = new EmailDto();

        Exception exception = assertThrows(EntityNotFoundException.class, () -> emailService.edit(emailDto));

        verify(emailRepository, times(1)).findById(any());
        assertEquals("Error! This email doesn't exist in our DB", exception.getMessage());
    }

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
        EmailDto emailFounded = emailService.getById(emailDto.id);

        assertEquals(emailDto.email, emailFounded.email);

        verify(emailMapper, times(1)).mapEmailToDto(email);
        verify(emailRepository, times(1)).findById(argThat(
                id -> id.intValue() == emailDto.id));
    }
}
