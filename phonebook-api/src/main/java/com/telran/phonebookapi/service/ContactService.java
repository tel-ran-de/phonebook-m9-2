package com.telran.phonebookapi.service;

import com.telran.phonebookapi.dto.ContactDto;
import com.telran.phonebookapi.mapper.ContactMapper;
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
    ContactMapper contactMapper;

    public ContactService(IUserRepository userRepository, IContactRepository contactRepository, ContactMapper contactMapper) {
        this.userRepository = userRepository;
        this.contactRepository = contactRepository;
        this.contactMapper = contactMapper;
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

    public void editLastName(ContactDto contactDto) {
        Contact contact = contactRepository.findById(contactDto.id).orElseThrow(() -> new EntityNotFoundException(CONTACT_DOES_NOT_EXIST));
        contact.setLastName(contactDto.lastName);
        contactRepository.save(contact);
    }

    public void editDescription(ContactDto contactDto) {
        Contact contact = contactRepository.findById(contactDto.id).orElseThrow(() -> new EntityNotFoundException(CONTACT_DOES_NOT_EXIST));
        contact.setDescription(contactDto.description);
        contactRepository.save(contact);
    }

    public ContactDto getById(int id) {
        Contact contact = contactRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(CONTACT_DOES_NOT_EXIST));
        ContactDto contactDto = contactMapper.mapContactToDto(contact);
        return contactDto;
    }

    public List<ContactDto> getByUserId(String userId) {
        return contactRepository.findAllByEmails(userId).stream()
                .map(contactMapper::mapContactToDto)
                .collect(Collectors.toList());
    }

    public void removeById(int id) {
        contactRepository.deleteById(id);
    }
}
