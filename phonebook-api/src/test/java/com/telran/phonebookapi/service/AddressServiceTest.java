package com.telran.phonebookapi.service;

import com.telran.phonebookapi.dto.AddressDto;
import com.telran.phonebookapi.mapper.AddressMapper;
import com.telran.phonebookapi.model.Address;
import com.telran.phonebookapi.model.Contact;
import com.telran.phonebookapi.model.User;
import com.telran.phonebookapi.persistance.IAddressRepository;
import com.telran.phonebookapi.persistance.IContactRepository;
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
class AddressServiceTest {

    @Mock
    IContactRepository contactRepository;

    @Mock
    IAddressRepository addressRepository;

    @InjectMocks
    AddressService addressService;

    @Spy
    AddressMapper addressMapper;

    @Test
    public void testAdd_contactExists_contactWithAddress() {
        User user = new User("test@gmail.com", "test");
        Contact contact = new Contact("Name", user);

        when(contactRepository.findById(contact.getId())).thenReturn(Optional.of(contact));

        AddressDto addressDto = new AddressDto();
        addressDto.contactId = 0;
        addressDto.city = "Berlin";
        addressService.add(addressDto);

        verify(addressRepository, times(1)).save(any());
        verify(addressRepository, times(1)).save(argThat(address -> address.getCity().equals(addressDto.city) &&
                address.getContact().getId() == addressDto.contactId)
        );
    }

    @Test
    public void testAdd_contactDoesNotExist_EntityNotFoundException() {

        AddressDto addressDto = new AddressDto();
        addressDto.contactId = 0;
        addressDto.city = "Berlin";

        Exception exception = assertThrows(EntityNotFoundException.class, () -> addressService.add(addressDto));

        verify(contactRepository, times(1)).findById(any());
        assertEquals("Error! This contact doesn't exist in our DB", exception.getMessage());
    }

    @Test
    public void testEditAllFields_addressExist_AllFieldsChanged() {

        User user = new User("test@gmail.com", "test");

        Contact oldContact = new Contact("TestName", user);
        Address oldAddress = new Address(oldContact);

        AddressDto addressDto = new AddressDto();
        addressDto.id = 0;
        addressDto.city = "Berlin";

        when(addressRepository.findById(addressDto.id)).thenReturn(Optional.of(oldAddress));

        addressService.editAllFields(addressDto);

        verify(addressRepository, times(1)).save(any());
        verify(addressRepository, times(1)).save(argThat(address ->
                address.getCity().equals(addressDto.city)
                        && address.getContact().getId() == addressDto.contactId)
        );
    }

    @Test
    public void testEditAny_addressDoesNotExist_EntityNotFoundException() {

        AddressDto addressDto = new AddressDto();

        Exception exception = assertThrows(EntityNotFoundException.class, () -> addressService.editAllFields(addressDto));

        verify(addressRepository, times(1)).findById(any());
        assertEquals("Error! This address doesn't exist in our DB", exception.getMessage());
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

    @Test
    public void testGetById_contactWithAddress_Address() {

        User user = new User("test@gmail.com", "test");
        Contact contact = new Contact("TestName", user);
        Address address = new Address("10000", "Germany", "Berlin", "Strasse", contact);

        AddressDto addressDto = new AddressDto(0, "10000", "Germany", "Berlin", "Strasse", 0);

        when(addressRepository.findById(addressDto.id)).thenReturn(Optional.of(address));
        AddressDto addressFounded = addressService.getById(addressDto.id);

        assertEquals(addressDto.zip, addressFounded.zip);
        assertEquals(addressDto.country, addressFounded.country);
        assertEquals(addressDto.city, addressFounded.city);
        assertEquals(addressDto.street, addressFounded.street);

        verify(addressMapper, times(1)).mapAddressToDto(address);
        verify(addressRepository, times(1)).findById(argThat(
                id -> id.intValue() == addressDto.id));
    }
}
