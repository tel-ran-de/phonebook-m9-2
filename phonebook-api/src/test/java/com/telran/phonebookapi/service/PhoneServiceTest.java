package com.telran.phonebookapi.service;

import com.telran.phonebookapi.dto.PhoneDto;
import com.telran.phonebookapi.model.Contact;
import com.telran.phonebookapi.model.CountryCode;
import com.telran.phonebookapi.model.Phone;
import com.telran.phonebookapi.model.User;
import com.telran.phonebookapi.persistance.IContactRepository;
import com.telran.phonebookapi.persistance.ICountryCodeRepository;
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

    @Mock
    ICountryCodeRepository countryCodeRepository;

    @InjectMocks
    PhoneService phoneService;

    @Test
    public void testAdd_contactExists_contactWithPhoneNumber() {
        User user = new User("test@gmail.com", "11111111");
        Contact contact = new Contact("TestName", user);
        CountryCode code = new CountryCode(49, "Germany");
        Phone number = new Phone(49, 12345678, contact);
        contact.addPhone(number);
        PhoneDto phoneDto = new PhoneDto(0, 49, 12345678, 0);

        when(contactRepository.findById(contact.getId())).thenReturn(Optional.of(contact));
        when(countryCodeRepository.findById(number.getCountryCode())).thenReturn(Optional.of(code));

        phoneService.add(phoneDto.contactId, phoneDto.countryCode, phoneDto.phoneNumber);

        verify(phoneRepository, times(1)).save(any());
        verify(phoneRepository, times(1)).save(argThat(phone ->
                phone.getPhoneNumber() == phoneDto.phoneNumber &&
                phone.getContact().getId() == phoneDto.contactId)
        );
    }

    @Test
    public void testAdd_contactDoesNotExist_EntityNotFoundException() {
        PhoneDto phoneDto = new PhoneDto(0, 49, 12345678, 0);

        Exception exception = assertThrows(EntityNotFoundException.class, () -> phoneService.add(
                phoneDto.contactId,
                phoneDto.countryCode,
                phoneDto.phoneNumber));

        verify(contactRepository, times(1)).findById(any());
        assertEquals("Error! This contact doesn't exist", exception.getMessage());
    }

    @Test
    public void testEditAllFields_phoneExist_AllFieldsChanged() {

        User user = new User("test@gmail.com", "11111111");
        Contact contact = new Contact("TestName", user);
        Phone oldNumber = new Phone(49, 87654321, contact);
        contact.addPhone(oldNumber);
        PhoneDto phoneDto = new PhoneDto(0, 49, 12345678, 0);

        when(phoneRepository.findById(phoneDto.id)).thenReturn(Optional.of(oldNumber));

        phoneService.editAllFields(phoneDto.contactId, phoneDto.countryCode, phoneDto.phoneNumber);

        verify(phoneRepository, times(1)).save(any());
        verify(phoneRepository, times(1)).save(argThat(phone ->
                phone.getPhoneNumber() == phoneDto.phoneNumber && phone.getContact().getId() == phoneDto.contactId)
        );
    }

    @Test
    public void testEditAny_phoneDoesNotExist_EntityNotFoundException() {

        PhoneDto phoneDto = new PhoneDto(0, 49, 12345678, 0);

        Exception exception = assertThrows(EntityNotFoundException.class, () -> phoneService.editAllFields(
                phoneDto.contactId,
                phoneDto.countryCode,
                phoneDto.phoneNumber));

        verify(phoneRepository, times(1)).findById(any());
        assertEquals("Error! This phone number doesn't exist", exception.getMessage());
    }

    @Captor
    ArgumentCaptor<Phone> phoneArgumentCaptor;

    @Test
    public void testRemoveById_phoneExists_PhoneDeleted() {

        User user = new User("test@gmail.com", "11111111");
        Contact contact = new Contact("TestName", user);
        Phone phone = new Phone(49, 12345678, contact);
        PhoneDto phoneDto = new PhoneDto(0, 49, 12345678, 0);

        when(phoneRepository.findById(phoneDto.id)).thenReturn(Optional.of(phone));

        phoneService.removeById(phoneDto.id);

        List<Phone> capturedAddresses = phoneArgumentCaptor.getAllValues();
        verify(phoneRepository, times(1)).deleteById(phoneDto.id);
        assertEquals(0, capturedAddresses.size());
    }

    @Test
    public void testGetById_contactWithPhone_PhoneNumber() {

        User user = new User("test@gmail.com", "11111111");
        Contact contact = new Contact("TestName", user);
        Phone phone = new Phone(49, 12345678, contact);
        PhoneDto phoneDto = new PhoneDto(0, 49, 12345678, 0);

        when(phoneRepository.findById(phoneDto.id)).thenReturn(Optional.of(phone));
        Phone phoneFounded = phoneService.getById(phoneDto.id);

        assertEquals(phoneDto.countryCode, phoneFounded.getCountryCode());
        assertEquals(phoneDto.phoneNumber, phoneFounded.getPhoneNumber());

        verify(phoneRepository, times(1)).findById(argThat(
                id -> id == phoneDto.id));
    }
}
