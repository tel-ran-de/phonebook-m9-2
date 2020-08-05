package com.telran.phonebookapi.service;

import com.telran.phonebookapi.dto.ContactDto;
import com.telran.phonebookapi.mapper.ContactMapper;
import com.telran.phonebookapi.model.Contact;
import com.telran.phonebookapi.model.User;
import com.telran.phonebookapi.persistance.IContactRepository;
import com.telran.phonebookapi.persistance.IUserRepository;
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
class ContactServiceTest {

    @Mock
    IUserRepository userRepository;

    @Mock
    IContactRepository contactRepository;

    @InjectMocks
    ContactService contactService;

    @Spy
    ContactMapper contactMapper;

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
    public void testEditAllFields_contactExist_AllFieldsChanged() {

        User user = new User("test@gmail.com", "test");

        Contact oldContact = new Contact("TestName", user);
        ContactDto contactDto = new ContactDto();
        contactDto.firstName = "NewName";
        contactDto.lastName = "NewLastName";
        contactDto.description = "newDescription";
        contactDto.userId = user.getEmail();

        when(contactRepository.findById(contactDto.id)).thenReturn(Optional.of(oldContact));

        contactService.editAllFields(contactDto);

        verify(contactRepository, times(1)).save(any());
        verify(contactRepository, times(1)).save(argThat(contact ->
                contact.getFirstName().equals(contactDto.firstName) && contact.getLastName().equals(contactDto.lastName) && contact.getDescription().equals(contactDto.description)
                        && contact.getUser().getEmail().equals(contactDto.userId)
        ));
    }

    @Test
    public void testEditAny_contactDoesNotExist_EntityNotFoundException() {

        ContactDto contactDto = new ContactDto();
        contactDto.firstName = "ContactName";
        contactDto.lastName = "LastName";
        contactDto.description = "Description";
        contactDto.userId = "wrong@gmail.com";

        Exception exception = assertThrows(EntityNotFoundException.class, () -> contactService.editAllFields(contactDto));

        verify(contactRepository, times(1)).findById(any());
        assertEquals("Error! This contact doesn't exist in our DB", exception.getMessage());
    }

    @Captor
    ArgumentCaptor<Contact> contactCaptor;

    @Test
    public void testRemoveById_contactExists_ContactDeleted() {

        User user = new User("test@gmail.com", "test");
        Contact contact = new Contact("Name", user);
        contact.setLastName("Surname");
        contact.setDescription("person");

        ContactDto contactDto = new ContactDto(1, "Name", "Surname", "person", "test@gmail.com");

        when(contactRepository.findById(contactDto.id)).thenReturn(Optional.of(contact));
        contactService.removeById(contactDto.id);

        List<Contact> capturedContacts = contactCaptor.getAllValues();
        verify(contactRepository, times(1)).deleteById(contactDto.id);
        assertEquals(0, capturedContacts.size());
    }

    @Test
    public void testGetById_userWithContact_Contact() {
        User user = new User("test@gmail.com", "test");
        Contact contact = new Contact("Name", user);
        contact.setLastName("Surname");
        contact.setDescription("person");

        ContactDto contactDto = new ContactDto(1, "Name", "Surname", "person", "test@gmail.com");

        when(contactRepository.findById(contactDto.id)).thenReturn(Optional.of(contact));
        ContactDto contactFounded = contactService.getById(contactDto.id);

        assertEquals(contactDto.firstName, contactFounded.firstName);
        assertEquals(contactDto.lastName, contactFounded.lastName);
        assertEquals(contactDto.description, contactFounded.description);

        verify(contactMapper, times(1)).mapContactToDto(contact);
        verify(contactRepository, times(1)).findById(argThat(
                id -> id.intValue() == contactDto.id));
    }
}
