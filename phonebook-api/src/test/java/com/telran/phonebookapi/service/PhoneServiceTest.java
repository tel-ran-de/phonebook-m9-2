package com.telran.phonebookapi.service;

import com.telran.phonebookapi.dto.PhoneDto;
import com.telran.phonebookapi.mapper.PhoneMapper;
import com.telran.phonebookapi.model.Contact;
import com.telran.phonebookapi.model.Phone;
import com.telran.phonebookapi.model.User;
import com.telran.phonebookapi.persistance.IContactRepository;
import com.telran.phonebookapi.persistance.IPhoneRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class PhoneServiceTest {

    @Mock
    IContactRepository contactRepository;

    @Mock
    IPhoneRepository phoneRepository;

    @InjectMocks
    PhoneService phoneService;

    @Spy
    PhoneMapper phoneMapper;

    @Test
    public void testAdd_contactExists_contactWithPhoneNumber() {
        User user = new User("test@gmail.com", "test");
        Contact contact = new Contact("Name", user);

        when(contactRepository.findById(contact.getId())).thenReturn(Optional.of(contact));

        PhoneDto phoneDto = new PhoneDto();
        phoneDto.contactId = 0;
        phoneDto.phoneNumber = "12345678";
        phoneService.add(phoneDto);

        verify(phoneRepository, times(1)).save(any());
        verify(phoneRepository, times(1)).save(argThat(phone -> phone.getPhoneNumber().equals(phoneDto.phoneNumber) &&
                phone.getContact().getId() == phoneDto.contactId)
        );
    }

    @Test
    public void testAdd_contactDoesNotExist_EntityNotFoundException() {

        PhoneDto phoneDto = new PhoneDto();
        phoneDto.contactId = 0;
        phoneDto.phoneNumber = "12345678";

        Exception exception = assertThrows(EntityNotFoundException.class, () -> phoneService.add(phoneDto));

        verify(contactRepository, times(1)).findById(any());
        assertEquals("Error! This contact doesn't exist in our DB", exception.getMessage());
    }

    @Test
    public void testEditAllFields_phoneExist_AllFieldsChanged() {

        User user = new User("test@gmail.com", "test");

        Contact oldContact = new Contact("TestName", user);
        Phone oldPhone = new Phone(oldContact);

        PhoneDto phoneDto = new PhoneDto();
        phoneDto.id = 0;
        phoneDto.phoneNumber = "12345678";
        phoneDto.countryCode = "+49";

        when(phoneRepository.findById(phoneDto.id)).thenReturn(Optional.of(oldPhone));

        phoneService.editAllFields(phoneDto);

        verify(phoneRepository, times(1)).save(any());
        verify(phoneRepository, times(1)).save(argThat(phone ->
                phone.getPhoneNumber().equals(phoneDto.phoneNumber) && phone.getCountryCode().equals(phoneDto.countryCode)
                        && phone.getContact().getId() == phoneDto.contactId)
        );
    }

    @Test
    public void testEditAny_phoneDoesNotExist_EntityNotFoundException() {

        PhoneDto phoneDto = new PhoneDto();

        Exception exception = assertThrows(EntityNotFoundException.class, () -> phoneService.editAllFields(phoneDto));

        verify(phoneRepository, times(1)).findById(any());
        assertEquals("Error! This phone number doesn't exist in our DB", exception.getMessage());
    }

    @Captor
    ArgumentCaptor<Phone> phoneArgumentCaptor;

    @Test
    public void testRemoveById_phoneExists_PhoneDeleted() {

        User user = new User("test@gmail.com", "test");

        Contact contact = new Contact("TestName", user);
        Phone phone = new Phone("+49", "12345678", contact);

        PhoneDto phoneDto = new PhoneDto();
        phoneDto.id = 0;

        when(phoneRepository.findById(phoneDto.id)).thenReturn(Optional.of(phone));

        phoneService.removeById(phoneDto.id);

        List<Phone> capturedAddresses = phoneArgumentCaptor.getAllValues();
        verify(phoneRepository, times(1)).deleteById(phoneDto.id);
        assertEquals(0, capturedAddresses.size());
    }

    @Test
    public void testGetById_contactWithPhone_PhoneNumber() {

        User user = new User("test@gmail.com", "test");
        Contact contact = new Contact("TestName", user);
        Phone phone = new Phone("+49", "12345678", contact);

        PhoneDto phoneDto = new PhoneDto(0, "+49", "12345678", 0);

        when(phoneRepository.findById(phoneDto.id)).thenReturn(Optional.of(phone));
        PhoneDto phoneFounded = phoneService.getById(phoneDto.id);

        assertEquals(phoneDto.countryCode, phoneFounded.countryCode);
        assertEquals(phoneDto.phoneNumber, phoneFounded.phoneNumber);

        verify(phoneMapper, times(1)).mapPhoneToDto(phone);
        verify(phoneRepository, times(1)).findById(argThat(
                id -> id.intValue() == phoneDto.id));
    }
}
