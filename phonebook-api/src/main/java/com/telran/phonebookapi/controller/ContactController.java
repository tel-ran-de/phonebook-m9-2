package com.telran.phonebookapi.controller;

import com.telran.phonebookapi.dto.ContactDto;
import com.telran.phonebookapi.service.ContactService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class ContactController {

    ContactService contactService;

    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }

    @PostMapping("api/user/{userId}/contact")
    public void addContact(@RequestBody ContactDto contactDto, @PathVariable String userId) {
        contactDto.userId = userId;
        contactService.add(contactDto);
    }

    @PutMapping("api/contact")
    public void editContactFirstName(@RequestBody @Valid ContactDto contactDto) {
        contactService.editFirstName(contactDto);
    }

    @GetMapping("api/user/{userId}/contact")
    public List<ContactDto> getContactsByUserId(@PathVariable String userId) {
        return contactService.getByUserId(userId);
    }

    @DeleteMapping("api/contact/{contactId}")
    public void removeById(@PathVariable int contactId) {
        contactService.removeById(contactId);
    }
}
