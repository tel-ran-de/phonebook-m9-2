package com.telran.phonebookapi.mapper;

import com.telran.phonebookapi.dto.ContactDto;
import com.telran.phonebookapi.model.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ContactMapperTest {

    ContactMapper contactMapper = new ContactMapper();

    @Test
    void ContactDto() {
        User user = new User("test@gmail.com", "11223344");
        Contact contact = new Contact("Name", user);

        ContactDto contactDto = new ContactDto(1, "Name", "LastName", "Description");

        ContactDto contactDtoMapped = contactMapper.mapContactToDto(contact);
        assertEquals(contactDto.firstName, contactDtoMapped.firstName);
    }

    @Test
    void ContactDtoFull() {
        User user = new User("test@gmail.com", "11223344");
        Contact contact = new Contact("Name", user);
        Phone phone = new Phone(49, 12345678, contact);
        contact.addPhone(phone);
        Address address = new Address("10000", "strasse", "Berlin", "Germany", contact);
        contact.addAddress(address);
        Email email = new Email("testtest@gmail.com", contact);
        contact.addEmail(email);

        ContactDto contactDtoMapped = contactMapper.mapContactToDtoFull(contact);

        assertEquals(1, contactDtoMapped.phoneNumbers.size());
        assertEquals(1, contactDtoMapped.addresses.size());
        assertEquals(1, contactDtoMapped.emails.size());
    }
}
