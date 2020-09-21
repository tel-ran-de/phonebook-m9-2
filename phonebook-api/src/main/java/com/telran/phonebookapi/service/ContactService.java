package com.telran.phonebookapi.service;

import com.telran.phonebookapi.model.*;
import com.telran.phonebookapi.persistance.*;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class ContactService {

    static final String CONTACT_DOES_NOT_EXIST = "Error! This contact doesn't exist";

    IUserRepository userRepository;
    IContactRepository contactRepository;

    public ContactService(IUserRepository userRepository, IContactRepository contactRepository) {
        this.userRepository = userRepository;
        this.contactRepository = contactRepository;
    }

    public void add(String firstName, String lastName, String description, String userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException(UserService.USER_DOES_NOT_EXIST));
        Contact contact = new Contact(firstName, user);
        contact.setLastName(lastName);
        contact.setDescription(description);
        contactRepository.save(contact);
    }

    public Contact getById(int id) {
        return contactRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(CONTACT_DOES_NOT_EXIST));
    }

    public void editContact(String firstName, String lastName, String description, int id) {
        Contact contact = contactRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(CONTACT_DOES_NOT_EXIST));
        contact.setFirstName(firstName);
        contact.setLastName(lastName);
        contact.setDescription(description);
        contactRepository.save(contact);
    }

    public void removeById(int id) {
        contactRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(CONTACT_DOES_NOT_EXIST));
        contactRepository.deleteById(id);
    }

    public List<Contact> getAllContactsByUserId(String email) {
        return contactRepository.findAllByUserEmail(email);
    }

}
