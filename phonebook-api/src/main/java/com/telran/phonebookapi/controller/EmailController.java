package com.telran.phonebookapi.controller;

import com.telran.phonebookapi.dto.EmailDto;
import com.telran.phonebookapi.mapper.EmailMapper;
import com.telran.phonebookapi.model.Contact;
import com.telran.phonebookapi.model.Email;
import com.telran.phonebookapi.service.ContactService;
import com.telran.phonebookapi.service.EmailService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/email")
public class EmailController {

    static final String INVALID_ACCESS = "Error! You have no permission";

    EmailService emailService;
    ContactService contactService;
    EmailMapper emailMapper;

    public EmailController(EmailService emailService, ContactService contactService, EmailMapper emailMapper) {
        this.emailService = emailService;
        this.contactService = contactService;
        this.emailMapper = emailMapper;
    }

    @PostMapping("")
    public void addEmail(Authentication auth, @RequestBody @Valid EmailDto emailDto) {
        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        Contact contact = contactService.getById(emailDto.contactId);
        if (!contact.getUser().getEmail().equals(userDetails.getUsername())) {
            throw new EntityNotFoundException(INVALID_ACCESS);
        }
        emailService.add(emailDto.contactId, emailDto.email);
    }

    @PutMapping("")
    public void editEmail(Authentication auth, @RequestBody @Valid EmailDto emailDto) {
        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        Contact contact = contactService.getById(emailDto.contactId);
        if (!contact.getUser().getEmail().equals(userDetails.getUsername())) {
            throw new EntityNotFoundException(INVALID_ACCESS);
        }
        emailService.edit(emailDto.id, emailDto.email);
    }

    @GetMapping("/{id}")
    public EmailDto getById(Authentication auth, @PathVariable int id) {
        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        Email email = emailService.getById(id);
        Contact contact = contactService.getById(email.getContact().getId());
        if (!contact.getUser().getEmail().equals(userDetails.getUsername())) {
            throw new EntityNotFoundException(INVALID_ACCESS);
        }
        return emailMapper.mapEmailToDto(emailService.getById(id));
    }

    @DeleteMapping("/{id}")
    public void removeById(Authentication auth, @PathVariable int id) {
        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        Email email = emailService.getById(id);
        Contact contact = contactService.getById(email.getContact().getId());
        if (!contact.getUser().getEmail().equals(userDetails.getUsername())) {
            throw new EntityNotFoundException(INVALID_ACCESS);
        }
        emailService.removeById(id);
    }

}
