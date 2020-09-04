package com.telran.phonebookapi.service;

import com.telran.phonebookapi.dto.EmailDto;
import com.telran.phonebookapi.mapper.EmailMapper;
import com.telran.phonebookapi.model.Contact;
import com.telran.phonebookapi.model.Email;
import com.telran.phonebookapi.persistance.IContactRepository;
import com.telran.phonebookapi.persistance.IEmailRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmailService {

    static final String EMAIL_DOES_NOT_EXIST = "Error! This email doesn't exist in our DB";

    IContactRepository contactRepository;
    IEmailRepository emailRepository;
    EmailMapper emailMapper;

    public EmailService(IContactRepository contactRepository, IEmailRepository iEmailRepository, EmailMapper emailMapper) {
        this.contactRepository = contactRepository;
        this.emailRepository = iEmailRepository;
        this.emailMapper = emailMapper;
    }

    public void add(EmailDto emailDto) {
        Contact contact = contactRepository.findById(emailDto.contactId).orElseThrow(() -> new EntityNotFoundException(ContactService.CONTACT_DOES_NOT_EXIST));
        Email email = new Email(emailDto.email, contact);
        emailRepository.save(email);
    }

    public void edit(EmailDto emailDto) {
        Email email = emailRepository.findById(emailDto.id).orElseThrow(() -> new EntityNotFoundException(EMAIL_DOES_NOT_EXIST));
        email.setEmail(emailDto.email);
        emailRepository.save(email);
    }

    public EmailDto getById(int id) {
        Email email = emailRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(EMAIL_DOES_NOT_EXIST));
        EmailDto emailDto = emailMapper.mapEmailToDto(email);
        return emailDto;
    }

    public void removeById(int id) {
        emailRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(EMAIL_DOES_NOT_EXIST));
        emailRepository.deleteById(id);
    }

    public List<EmailDto> getAllEmailsByContactId(int contactId) {
        return emailRepository.findAllByContactId(contactId).stream()
                .map(emailMapper::mapEmailToDto)
                .collect(Collectors.toList());
    }
}
