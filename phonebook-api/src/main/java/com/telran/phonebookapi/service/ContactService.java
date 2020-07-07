package com.telran.phonebookapi.service;

import com.telran.phonebookapi.dto.ContactDto;
import com.telran.phonebookapi.model.Contact;
import com.telran.phonebookapi.model.User;
import com.telran.phonebookapi.persistance.IContactRepository;
import com.telran.phonebookapi.persistance.IUserRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ContactService {

    static final String CONTACT_DOES_NOT_EXIST = "Error! This contact doesn't exist in our DB";

    IUserRepository userRepository;
    IContactRepository contactRepository;

    public ContactService(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void add(ContactDto contactDto) {
        User user = userRepository.findById(contactDto.userId).orElseThrow(() -> new EntityNotFoundException(UserService.USER_DOES_NOT_EXIST));
        Contact contact = new Contact(contactDto.firstName, user);
        contactRepository.save(contact);
    }

    public void editFirstName(ContactDto contactDto) {
        Contact contact = contactRepository.findById(contactDto.id).orElseThrow(() -> new EntityNotFoundException(CONTACT_DOES_NOT_EXIST));
        contact.setFirstName(contactDto.firstName);
        contactRepository.save(contact);
    }

    public List<ContactDto> getByUserId(String userId) {
        List<Contact> contacts = contactRepository.findAllByEmails(userId);
        List<ContactDto> contactDtos = contacts.stream().map(contact -> {
            ContactDto contactDto = new ContactDto();
            contactDto.id = contact.getId();
            contactDto.firstName = contact.getFirstName();
            contactDto.lastName = contact.getLastName();
            contactDto.description = contact.getDescription();
            contactDto.userId = contact.getUser().getEmail();
            return contactDto;
        }).collect(Collectors.toList());
        return contactDtos;
    }

    public void removeById(int id) {
        contactRepository.deleteById(id);
    }
}
