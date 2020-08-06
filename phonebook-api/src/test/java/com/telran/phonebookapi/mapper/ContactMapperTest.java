package com.telran.phonebookapi.mapper;

import com.telran.phonebookapi.dto.ContactDto;
import com.telran.phonebookapi.model.Address;
import com.telran.phonebookapi.model.Contact;
import com.telran.phonebookapi.model.User;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ContactMapperTest {

    ContactMapper contactMapper = new ContactMapper();

    @Test
    void ContactDto() {
        User user = new User("test@gmail.com", "112233");
        Contact contact = new Contact("Name", user);

        ContactDto contactDto = new ContactDto(1, "Name", "LastName", "Description", "test@gmail.com");

        ContactDto contactDtoMapped = contactMapper.mapContactToDto(contact);
        assertEquals(contactDto.userId, contactDtoMapped.userId);
        assertEquals(contactDto.firstName, contactDtoMapped.firstName);
    }
}
