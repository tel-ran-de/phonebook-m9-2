package com.telran.phonebookapi.service;

import com.telran.phonebookapi.dto.AddressDto;
import com.telran.phonebookapi.mapper.AddressMapper;
import com.telran.phonebookapi.model.Address;
import com.telran.phonebookapi.model.Contact;
import com.telran.phonebookapi.model.Email;
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

    @Spy
    AddressMapper addressMapper;

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
        addressService.add("12345", "Germany", "Berlin", "Street", contact.getId());

        verify(addressRepository, times(1)).save(any());
        verify(addressRepository).save(addressCaptor.capture());

        List<Address> capturedEmails = addressCaptor.getAllValues();
        assertEquals(1, capturedEmails.size());
        assertEquals(address.getCity(), capturedEmails.get(0).getCity());
    }

    @Test
    public void testAdd_contactDoesNotExist_EntityNotFoundException() {
        Exception exception = assertThrows(EntityNotFoundException.class, ()
                ->addressService.add("12345", "Germany", "Berlin", "Street", 1));

        assertEquals(error_ContactDoesNotExist, exception.getMessage());
    }

    @Test
    public void testEditAllFields_addressExist_AllFieldsChanged() {

        User user = new User("test@gmail.com", "test");
        Contact contact = new Contact("TestName", user);

        when(contactRepository.findById(contact.getId())).thenReturn(Optional.of(contact));

        Address address = new Address("12345", "Germany", "Berlin", "Street", contact);
        addressService.add(address.getZip(), address.getCountry(), address.getCity(), address.getStreet(), address.getContact().getId());

        when(addressRepository.findById(address.getId())).thenReturn(Optional.of(address));

        addressService.editAllFields("new12345", "newGermany", "newBerlin", "newStreet", address.getId());

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
                        "new12345",
                        "newGermany",
                        "newBerlin",
                        "newStreet",
                        1));

        assertEquals(error_AddressDoesNotExist, exception.getMessage());
    }

    @Captor
    ArgumentCaptor<Address> addressArgumentCaptor;

    @Test
    public void testRemoveById_addressExists_AddressDeleted() {

        User user = new User("test@gmail.com", "test");

        Contact contact = new Contact("TestName", user);
        Address address = new Address();
        contact.addAddress(address);

        AddressDto addressDto = new AddressDto();
        addressDto.id = 0;

        when(addressRepository.findById(addressDto.id)).thenReturn(Optional.of(address));

        addressService.removeById(addressDto.id);

        List<Address> capturedAddresses = addressArgumentCaptor.getAllValues();
        verify(addressRepository, times(1)).deleteById(addressDto.id);
        assertEquals(0, capturedAddresses.size());
    }

//    @Test
//    public void testGetById_contactWithAddress_Address() {
//
//        User user = new User("test@gmail.com", "test");
//        Contact contact = new Contact("TestName", user);
//        Address address = new Address("10000", "Germany", "Berlin", "Strasse", contact);
//
//        AddressDto addressDto = new AddressDto(0, "10000", "Germany", "Berlin", "Strasse", 0);
//
//        when(addressRepository.findById(addressDto.id)).thenReturn(Optional.of(address));
//        AddressDto addressFounded = addressService.getById(addressDto.id);
//
//        assertEquals(addressDto.zip, addressFounded.zip);
//        assertEquals(addressDto.country, addressFounded.country);
//        assertEquals(addressDto.city, addressFounded.city);
//        assertEquals(addressDto.street, addressFounded.street);
//
//        verify(addressMapper, times(1)).mapAddressToDto(address);
//        verify(addressRepository, times(1)).findById(argThat(
//                id -> id.intValue() == addressDto.id));
//    }
}
