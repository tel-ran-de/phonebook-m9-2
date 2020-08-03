package com.telran.phonebookapi.service;

import com.telran.phonebookapi.dto.ContactDto;
import com.telran.phonebookapi.model.Contact;
import com.telran.phonebookapi.model.User;
import com.telran.phonebookapi.persistance.IContactRepository;
import com.telran.phonebookapi.persistance.IUserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class ContactServiceTest {

    @Mock
    IUserRepository userRepository;

    @Mock
    IContactRepository contactRepository;

    @InjectMocks
    ContactService contactService;

    @Test
    public void testAdd_userExists_userWithContact() {

        User user = new User("test@gmail.com", "test");
        when(userRepository.findById(user.getEmail())).thenReturn(Optional.of(user));

        ContactDto contactDto = new ContactDto();
        contactDto.firstName = "ContactName";
        contactDto.userId = user.getEmail();
        contactService.add(contactDto);

        verify(contactRepository, times(1)).save(any());
        verify(contactRepository, times(1)).save(argThat(contact ->
                contact.getFirstName().equals(contactDto.firstName)
                        && contact.getUser().getEmail().equals(contactDto.userId)
        ));
    }

    @Test
    public void testAdd_userDoesNotExist_EntityNotFoundException() {

        ContactDto contactDto = new ContactDto();
        contactDto.firstName = "ContactName";
        contactDto.userId = "wrong@gmail.com";

        Exception exception = assertThrows(EntityNotFoundException.class, () -> contactService.add(contactDto));

        verify(userRepository, times(1)).findById(anyString());
        assertEquals("Error! This user doesn't exist in our DB", exception.getMessage());
    }

    @Test
    public void testEditFirstName_contactExist_FirstNameChanged() {

        User user = new User("test@gmail.com", "test");

        Contact oldContact = new Contact("TestName", user);
        ContactDto contactDto = new ContactDto();
        contactDto.firstName = "NewName";
        contactDto.userId = user.getEmail();

        when(contactRepository.findById(contactDto.id)).thenReturn(Optional.of(oldContact));

        contactService.editFirstName(contactDto);

        verify(contactRepository, times(1)).save(any());
        verify(contactRepository, times(1)).save(argThat(contact ->
                contact.getFirstName().equals(contactDto.firstName)
                        && contact.getUser().getEmail().equals(contactDto.userId)
        ));
    }

    @Test
    public void testEditLastName_contactExist_LastNameChanged() {

        User user = new User("test@gmail.com", "test");

        Contact oldContact = new Contact("TestName", user);
        oldContact.setLastName("LastName");

        ContactDto updatedContactDto = new ContactDto();
        updatedContactDto.lastName = "NewLastName";
        updatedContactDto.userId = user.getEmail();

        when(contactRepository.findById(updatedContactDto.id)).thenReturn(Optional.of(oldContact));

        contactService.editLastName(updatedContactDto);

        verify(contactRepository, times(1)).save(any());
        verify(contactRepository, times(1)).save(argThat(contact ->
                contact.getLastName().equals(updatedContactDto.lastName)
                        && contact.getUser().getEmail().equals(updatedContactDto.userId)
        ));
    }

    @Test
    public void testEditDescription_contactExist_DescriptionChanged() {

        User user = new User("test@gmail.com", "test");

        Contact oldContact = new Contact("TestName", user);
        oldContact.setDescription("Description");

        ContactDto updatedContactDto = new ContactDto();
        updatedContactDto.description = "NewDescription";
        updatedContactDto.userId = user.getEmail();

        when(contactRepository.findById(updatedContactDto.id)).thenReturn(Optional.of(oldContact));

        contactService.editDescription(updatedContactDto);

        verify(contactRepository, times(1)).save(any());
        verify(contactRepository, times(1)).save(argThat(contact ->
                contact.getDescription().equals(updatedContactDto.description)
                        && contact.getUser().getEmail().equals(updatedContactDto.userId)
        ));
    }

    @Test
    public void testEditAny_contactDoesNotExist_EntityNotFoundException() {

        ContactDto contactDto = new ContactDto();
        contactDto.firstName = "ContactName";
        contactDto.lastName = "LastName";
        contactDto.description = "Description";
        contactDto.userId = "wrong@gmail.com";

        Exception exception01 = assertThrows(EntityNotFoundException.class, () -> contactService.editFirstName(contactDto));
        Exception exception02 = assertThrows(EntityNotFoundException.class, () -> contactService.editLastName(contactDto));
        Exception exception03 = assertThrows(EntityNotFoundException.class, () -> contactService.editDescription(contactDto));

        verify(contactRepository, times(3)).findById(any());
        assertEquals("Error! This contact doesn't exist in our DB", exception01.getMessage());
        assertEquals("Error! This contact doesn't exist in our DB", exception02.getMessage());
        assertEquals("Error! This contact doesn't exist in our DB", exception03.getMessage());

    }

    @Test
    public void testRemoveById_contactExists_ContactDeleted() {

        User user = new User("test@gmail.com", "test");
        Contact contact = new Contact("Name", user);
        contact.setLastName("Surname");
        contact.setDescription("person");

        ContactDto contactDto = new ContactDto(1, "Name", "Surname", "person", "test@gmail.com");

        contactService.removeById(contactDto.id);

        List<Contact> allContacts = new ArrayList<>();
        allContacts.add(contact);

        when(contactRepository.findAllByEmails(user.getEmail())).thenReturn(allContacts);
        List<ContactDto> allContactsDto = contactService.getByUserId(user.getEmail());

        verify(contactRepository, times(1)).deleteById(contactDto.id);
        assertEquals(0, allContactsDto.size());

    }
}
