package com.telran.phonebookapi.controller;

import com.telran.phonebookapi.dto.ContactDto;
import com.telran.phonebookapi.mapper.ContactMapper;
import com.telran.phonebookapi.service.ContactService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/contact")
public class ContactController {

    ContactService contactService;
    ContactMapper contactMapper;

    public ContactController(ContactService contactService, ContactMapper contactMapper) {
        this.contactService = contactService;
        this.contactMapper = contactMapper;
    }

    @PostMapping("")
    public void addContact(@Valid @RequestBody ContactDto contactDto) {
        contactService.add(contactDto.firstName, contactDto.lastName, contactDto.description, contactDto.userId);
    }

    @GetMapping("/{id}")
    public ContactDto getById(@PathVariable int id) {
        return contactMapper.mapContactToDtoFull(contactService.getById(id));
    }

    @PutMapping("")
    public void editContact(@Valid @RequestBody ContactDto contactDto) {
        contactService.editAllFields(contactDto.firstName, contactDto.lastName, contactDto.description, contactDto.id);
    }

    @DeleteMapping("/{id}")
    public void removeById(@PathVariable int id) {
        contactService.removeById(id);
    }

    @GetMapping("all/{email}")
    public List<ContactDto> requestAllContactsByUserEmail(@PathVariable String email) {
        return contactService.getAllContactsByUserId(email).stream()
                .map(contactMapper::mapContactToDto).collect(Collectors.toList());
    }

    @PutMapping("/profile")
    public void editProfile(@Valid @RequestBody ContactDto contactDto) {
        contactService.editProfile(contactDto.firstName, contactDto.lastName, contactDto.description, contactDto.id);
    }

}
