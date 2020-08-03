package com.telran.phonebookapi.mapper;

import com.telran.phonebookapi.dto.ContactDto;
import com.telran.phonebookapi.model.Contact;
import org.springframework.stereotype.Component;

@Component
public class ContactMapper {

    public ContactDto mapContactToDto(Contact contact) {
        return new ContactDto(contact.getId(), contact.getFirstName(), contact.getLastName(), contact.getDescription(), contact.getUser().getEmail());
    }
}
