package com.telran.phonebookapi.mapper;

import com.telran.phonebookapi.dto.ContactDto;
import com.telran.phonebookapi.model.Contact;
import org.springframework.stereotype.Component;

@Component
public class ContactMapper {

    PhoneMapper phoneMapper = new PhoneMapper();
    AddressMapper addressMapper = new AddressMapper();
    EmailMapper emailMapper = new EmailMapper();

    public ContactDto mapContactToDtoFull(Contact contact) {
        return new ContactDto(contact.getId(),
                contact.getFirstName(),
                contact.getLastName(),
                contact.getDescription(),
                phoneMapper.mapListPhoneToDto(contact.getPhoneNumbers()),
                addressMapper.mapListAddressToDto(contact.getAddresses()),
                emailMapper.mapListEmailToDto(contact.getEmails()));
    }

    public ContactDto mapContactToDto(Contact contact) {
        return new ContactDto(contact.getId(),
                contact.getFirstName(),
                contact.getLastName(),
                contact.getDescription());
    }
}
