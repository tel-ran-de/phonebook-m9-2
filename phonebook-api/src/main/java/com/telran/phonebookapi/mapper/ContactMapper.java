package com.telran.phonebookapi.mapper;

import com.telran.phonebookapi.dto.AddressDto;
import com.telran.phonebookapi.dto.ContactDto;
import com.telran.phonebookapi.dto.EmailDto;
import com.telran.phonebookapi.dto.PhoneDto;
import com.telran.phonebookapi.model.Contact;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ContactMapper {

    public ContactDto mapContactToDtoFull(Contact contact, List<PhoneDto> allPhonesByContact, List<AddressDto> allAddressesByContact, List<EmailDto> allEmailsByContact) {
        return new ContactDto(contact.getId(),
                contact.getFirstName(),
                contact.getLastName(),
                contact.getDescription(),
                contact.getUser().getEmail(),
                allPhonesByContact,
                allAddressesByContact,
                allEmailsByContact);
    }

    public ContactDto mapContactToDto(Contact contact) {
        return new ContactDto(contact.getId(),
                contact.getFirstName(),
                contact.getLastName(),
                contact.getDescription(),
                contact.getUser().getEmail());
    }
}
