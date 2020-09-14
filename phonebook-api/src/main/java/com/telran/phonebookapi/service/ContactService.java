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
    IAddressRepository addressRepository;
    IPhoneRepository phoneRepository;
    IEmailRepository emailRepository;

    public ContactService(IUserRepository userRepository, IContactRepository contactRepository, IAddressRepository addressRepository, IPhoneRepository phoneRepository, IEmailRepository emailRepository) {
        this.userRepository = userRepository;
        this.contactRepository = contactRepository;
        this.addressRepository = addressRepository;
        this.phoneRepository = phoneRepository;
        this.emailRepository = emailRepository;
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

    public Contact getByIdFullDetails(int id) {
        Contact contact = contactRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(CONTACT_DOES_NOT_EXIST));
        List<Phone> numbers = phoneRepository.findAllByContactId(contact.getId());
        List<Address> addresses = addressRepository.findAllByContactId(contact.getId());
        List<Email> emails = emailRepository.findAllByContactId(contact.getId());
        contact.addPhones(numbers);
        contact.addAddresses(addresses);
        contact.addEmails(emails);
        return contact;
    }

    public void editAllFields(String firstName, String lastName, String description, int id) {
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

    public List<Contact> getAllContactsByUserId(String userId) {
        return contactRepository.findAllByUserEmail(userId);
    }

//    public void addProfile(String firstName, String lastName, String description, String userId) {
//        User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException(UserService.USER_DOES_NOT_EXIST));
//        Contact profile = user.getMyProfile();
//        profile.setFirstName(firstName);
//        profile.setLastName(lastName);
//        profile.setDescription(description);
//        profile.setUser(user);
//        user.addProfile(profile);
//        contactRepository.save(profile);
//    }

    public void editProfile(String firstName, String lastName, String description, int id) {
        Contact newProfile = contactRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(CONTACT_DOES_NOT_EXIST));
        newProfile.setFirstName(firstName);
        newProfile.setLastName(lastName);
        newProfile.setDescription(description);
        contactRepository.save(newProfile);
    }

}
