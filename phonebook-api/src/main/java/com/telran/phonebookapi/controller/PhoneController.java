package com.telran.phonebookapi.controller;

import com.telran.phonebookapi.dto.PhoneDto;
import com.telran.phonebookapi.mapper.PhoneMapper;
import com.telran.phonebookapi.model.Contact;
import com.telran.phonebookapi.service.ContactService;
import com.telran.phonebookapi.service.PhoneService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/phone")
public class PhoneController {

    static final String INVALID_ACCESS = "Error! You have no permission";

    PhoneService phoneService;
    ContactService contactService;
    PhoneMapper phoneMapper;

    public PhoneController(PhoneService phoneService, ContactService contactService, PhoneMapper phoneMapper) {
        this.phoneService = phoneService;
        this.contactService = contactService;
        this.phoneMapper = phoneMapper;
    }

    @PostMapping("")
    public void addPhone(Authentication auth, @RequestBody @Valid PhoneDto phoneDto) {
        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        Contact contact = contactService.getById(phoneDto.contactId);
        if (!contact.getUser().getEmail().equals(userDetails.getUsername())) {
            throw new EntityNotFoundException(INVALID_ACCESS);
        }
        phoneService.add(phoneDto.contactId, phoneDto.countryCode, phoneDto.phoneNumber);
    }

    @PutMapping("")
    public void editPhone(Authentication auth, @RequestBody @Valid PhoneDto phoneDto) {
        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        Contact contact = contactService.getById(phoneDto.contactId);
        if (!contact.getUser().getEmail().equals(userDetails.getUsername())) {
            throw new EntityNotFoundException(INVALID_ACCESS);
        }
        phoneService.editAllFields(phoneDto.contactId, phoneDto.countryCode, phoneDto.phoneNumber);
    }

    @GetMapping("/{id}")
    public PhoneDto getById(Authentication auth, @PathVariable int id) {
        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        Contact contact = contactService.getById(id);
        if (!contact.getUser().getEmail().equals(userDetails.getUsername())) {
            throw new EntityNotFoundException(INVALID_ACCESS);
        }
        return phoneMapper.mapPhoneToDto(phoneService.getById(id));
    }

    @DeleteMapping("/{id}")
    public void removeById(Authentication auth, @PathVariable int id) {
        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        Contact contact = contactService.getById(id);
        if (!contact.getUser().getEmail().equals(userDetails.getUsername())) {
            throw new EntityNotFoundException(INVALID_ACCESS);
        }
        phoneService.removeById(id);
    }

    @GetMapping("/{contactId}")
    public List<PhoneDto> getAllPhoneNumbers(Authentication auth, @PathVariable int contactId) {
        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        Contact contact = contactService.getById(contactId);
        if (!contact.getUser().getEmail().equals(userDetails.getUsername())) {
            throw new EntityNotFoundException(INVALID_ACCESS);
        }
        return phoneService.getAllPhoneNumbersByContactId(contactId).stream()
                .map(phoneMapper::mapPhoneToDto).collect(Collectors.toList());
    }
}
