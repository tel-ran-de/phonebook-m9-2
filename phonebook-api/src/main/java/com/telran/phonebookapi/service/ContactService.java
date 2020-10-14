package com.telran.phonebookapi.service;

import com.telran.phonebookapi.model.*;
import com.telran.phonebookapi.persistance.*;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ContactService {

    static final String CONTACT_DOES_NOT_EXIST = "Error! This contact doesn't exist";

    IUserRepository userRepository;
    IContactRepository contactRepository;

    public ContactService(IUserRepository userRepository, IContactRepository contactRepository) {
        this.userRepository = userRepository;
        this.contactRepository = contactRepository;
    }

    public Contact add(String userId, String firstName, String lastName, String description) {
        User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException(UserService.USER_DOES_NOT_EXIST));
        Contact contact = new Contact(firstName, user);
        contact.setLastName(lastName);
        contact.setDescription(description);
        contactRepository.save(contact);
        return contact;
    }

    public Contact getById(int id) {
        return contactRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(CONTACT_DOES_NOT_EXIST));
    }

    public void editContact(int id, String firstName, String lastName, String description) {
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
        User user = userRepository.findById(email).orElseThrow(() -> new EntityNotFoundException(UserService.USER_DOES_NOT_EXIST));
        return user.getContacts().stream()
                .filter(contact -> contact.getId() != user.getMyProfile().getId())
                .collect(Collectors.toList());
    }

    public List<Phone> getPhones(int id) {
        Contact contact = contactRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(CONTACT_DOES_NOT_EXIST));
        return contact.getPhones();
    }

    public List<Address> getAddresses(int id) {
        Contact contact = contactRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(CONTACT_DOES_NOT_EXIST));
        return contact.getAddresses();
    }

    public List<Email> getEmails(int id) {
        Contact contact = contactRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(CONTACT_DOES_NOT_EXIST));
        return contact.getEmails();
    }
}
