package com.telran.phonebookapi.service;

import com.telran.phonebookapi.mapper.EmailMapper;
import com.telran.phonebookapi.model.Contact;
import com.telran.phonebookapi.model.Email;
import com.telran.phonebookapi.persistance.IContactRepository;
import com.telran.phonebookapi.persistance.IEmailRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
public class EmailService {

    static final String EMAIL_DOES_NOT_EXIST = "Error! This email doesn't exist";

    IContactRepository contactRepository;
    IEmailRepository emailRepository;
    EmailMapper emailMapper;

    public EmailService(IContactRepository contactRepository,
                        IEmailRepository iEmailRepository,
                        EmailMapper emailMapper) {
        this.contactRepository = contactRepository;
        this.emailRepository = iEmailRepository;
        this.emailMapper = emailMapper;
    }

    public void add(int contactId, String email) {
        Contact contact = contactRepository.findById(contactId).orElseThrow(()
                -> new EntityNotFoundException(ContactService.CONTACT_DOES_NOT_EXIST));
        Email newEmail = new Email(email, contact);
        emailRepository.save(newEmail);
    }

    public void edit(int emailId, String newEmail) {
        Email email = emailRepository.findById(emailId).orElseThrow(()
                -> new EntityNotFoundException(EMAIL_DOES_NOT_EXIST));
        email.setEmail(newEmail);
        emailRepository.save(email);
    }

    public Email getById(int id) {
        return emailRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(EMAIL_DOES_NOT_EXIST));
    }

    public void removeById(int id) {
        emailRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(EMAIL_DOES_NOT_EXIST));
        emailRepository.deleteById(id);
    }

}
