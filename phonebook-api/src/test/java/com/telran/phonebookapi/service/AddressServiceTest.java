package com.telran.phonebookapi.service;

import com.telran.phonebookapi.dto.AddressDto;
import com.telran.phonebookapi.model.Address;
import com.telran.phonebookapi.model.Contact;
import com.telran.phonebookapi.model.User;
import com.telran.phonebookapi.persistance.IAddressRepository;
import com.telran.phonebookapi.persistance.IContactRepository;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@TestMethodOrder(MethodOrderer.Alphanumeric.class)
@ExtendWith(MockitoExtension.class)
class AddressServiceTest {

    @Mock
    IContactRepository contactRepository;

    @Mock
    IAddressRepository addressRepository;

    @InjectMocks
    AddressService addressService;

    @InjectMocks
    List<Address> addresses = new ArrayList<>();

    @Captor
    ArgumentCaptor<Address> addressCaptor;

    String error_AddressDoesNotExist = "Error! This address doesn't exist";
    String error_ContactDoesNotExist = "Error! This contact doesn't exist";

    @Test
    public void testAdd_contactExists_contactWithAddress() {
        User user = new User("test@gmail.com", "test");
        Contact contact = new Contact("Name", user);

        when(contactRepository.findById(contact.getId())).thenReturn(Optional.of(contact));

        Address address = new Address("12345", "Germany", "Berlin", "Street", contact);
        addressService.add(contact.getId(), "12345", "Germany", "Berlin", "Street");

        verify(addressRepository, times(1)).save(any());
        verify(addressRepository).save(addressCaptor.capture());

        List<Address> capturedEmails = addressCaptor.getAllValues();
        assertEquals(1, capturedEmails.size());
        assertEquals(address.getCity(), capturedEmails.get(0).getCity());
    }

    @Test
    public void testAdd_contactDoesNotExist_EntityNotFoundException() {
        Exception exception = assertThrows(EntityNotFoundException.class, ()
                -> addressService.add(1, "12345", "Germany", "Berlin", "Street"));

        AddressDto addressDto = new AddressDto();
        addressDto.contactId = 0;
        addressDto.city = "Berlin";

        verify(contactRepository, times(1)).findById(any());
        assertEquals("Error! This contact doesn't exist", exception.getMessage());
    }

    @Test
    public void testEdit_addressExist_AllFieldsChanged() {

        User user = new User("test@gmail.com", "test");
        Contact contact = new Contact("TestName", user);

        when(contactRepository.findById(contact.getId())).thenReturn(Optional.of(contact));

        Address address = new Address("12345", "Germany", "Berlin", "Street", contact);
        addressService.add(address.getContact().getId(), address.getZip(), address.getCountry(), address.getCity(), address.getStreet());

        when(addressRepository.findById(address.getId())).thenReturn(Optional.of(address));

        addressService.editAllFields(address.getId(), "new12345", "newGermany", "newBerlin", "newStreet");

        verify(addressRepository, times(2)).save(any());

        Address addressFounded = addressService.getById(address.getId());
        assertEquals(address.getCity(), addressFounded.getCity());
        assertEquals(address.getCountry(), addressFounded.getCountry());
        assertEquals(address.getStreet(), addressFounded.getStreet());
        assertEquals(address.getZip(), addressFounded.getZip());
        assertEquals(contact.getId(), addressFounded.getContact().getId());
        assertEquals(address.getId(), addressFounded.getId());

        verify(addressRepository, times(2)).findById(argThat(id ->
                id == address.getId()));
    }

    @Test
    public void testEdit_addressDoesNotExist_EntityNotFoundException() {
        Exception exception = assertThrows(EntityNotFoundException.class, ()
                -> addressService.editAllFields(
                1,
                "new12345",
                "newGermany",
                "newBerlin",
                "newStreet"));

        assertEquals(error_AddressDoesNotExist, exception.getMessage());
    }

    @Test
    public void testRemoveById_addressExists_AddressDeleted() {
        User user = new User("test@gmail.com", "test");
        Contact contact = new Contact("TestName", user);
        Address address = new Address("12345", "Germany", "Berlin", "Street", contact);

        when(addressRepository.findById(address.getId())).thenReturn(Optional.of(address));

        addressService.removeById(address.getId());

        List<Address> capturedAddresses = addressCaptor.getAllValues();
        verify(addressRepository, times(1)).deleteById(address.getId());
        assertEquals(0, capturedAddresses.size());
    }

    @Test
    public void testRemoveById_emailDoesNotExist_EntityNotFoundException() {
        Exception exception = assertThrows(EntityNotFoundException.class, ()
                -> addressService.removeById(1));

        assertEquals(error_AddressDoesNotExist, exception.getMessage());
    }

    @Test
    public void testGetById_oneAddress_AddressFounded() {

        User user = new User("test@gmail.com", "test");
        Contact contact = new Contact("TestName", user);
        Address address = new Address("10000", "Germany", "Berlin", "Strasse", contact);

        when(addressRepository.findById(address.getId())).thenReturn(Optional.of(address));
        Address addressFounded = addressService.getById(address.getId());

        assertEquals(address.getZip(), addressFounded.getZip());
        assertEquals(address.getCountry(), addressFounded.getCountry());
        assertEquals(address.getCity(), addressFounded.getCity());
        assertEquals(address.getStreet(), addressFounded.getStreet());

        verify(addressRepository, times(1)).findById(argThat(
                id -> id == address.getId()));
    }

    @Test
    public void testGetById_threeAddresses_allFounded() {

        User user = new User("test@gmail.com", "test");
        Contact contact = new Contact("TestName", user);

        when(contactRepository.findById(contact.getId())).thenReturn(Optional.of(contact));

        Address address = new Address("10000", "Germany", "Berlin", "Strasse", contact);
        Address address2 = new Address("10002", "Germany2", "Berlin2", "Strasse2", contact);
        Address address3 = new Address("10003", "Germany3", "Berlin3", "Strasse3", contact);
        addressService.add(address.getContact().getId(), address.getZip(), address.getCountry(),
                address.getCity(), address.getStreet());
        addressService.add(address.getContact().getId(), address2.getZip(), address2.getCountry(),
                address2.getCity(), address2.getStreet());
        addressService.add(address.getContact().getId(), address3.getZip(), address3.getCountry(),
                address3.getCity(), address3.getStreet());

        when(addressRepository.findById(address2.getId())).thenReturn(Optional.of(address2));

        Address addressFounded = addressService.getById(address2.getId());

        assertEquals(address2.getZip(), addressFounded.getZip());
        assertEquals(address2.getCountry(), addressFounded.getCountry());
        assertEquals(address2.getCity(), addressFounded.getCity());
        assertEquals(address2.getStreet(), addressFounded.getStreet());
    }

    @Test
    public void testGetById_addressDoesNotExist_EntityNotFoundException() {
        Exception exception = assertThrows(EntityNotFoundException.class, ()
                -> addressService.getById(2));

        assertEquals(error_AddressDoesNotExist, exception.getMessage());
    }

}
