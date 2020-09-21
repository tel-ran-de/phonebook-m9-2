package com.telran.phonebookapi.controller;

import com.telran.phonebookapi.dto.ContactDto;
import com.telran.phonebookapi.mapper.ContactMapper;
import com.telran.phonebookapi.model.Contact;
import com.telran.phonebookapi.model.User;
import com.telran.phonebookapi.service.ContactService;
import com.telran.phonebookapi.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/contact")
public class ContactController {

    static final String INVALID_ACCESS = "Error! You have no permission";

    UserService userService;
    ContactService contactService;
    ContactMapper contactMapper;

    public ContactController(UserService userService, ContactService contactService, ContactMapper contactMapper) {
        this.userService = userService;
        this.contactService = contactService;
        this.contactMapper = contactMapper;
    }

    @PostMapping("")
    public void addContact(Authentication auth, @Valid @RequestBody ContactDto contactDto) {
        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        contactService.add(contactDto.firstName, contactDto.lastName, contactDto.description, userDetails.getUsername());
    }

    @GetMapping("/{id}")
    public ContactDto getById(Authentication auth, @PathVariable int id) {
        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        Contact contact = contactService.getById(id);
        if (!contact.getUser().getEmail().equals(userDetails.getUsername())) {
            throw new EntityNotFoundException(INVALID_ACCESS);
        }
        return contactMapper.mapContactToDtoFull(contactService.getById(id));
    }

    @PutMapping("")
    public void editContact(Authentication auth, @Valid @RequestBody ContactDto contactDto) {
        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        Contact contact = contactService.getById(contactDto.id);
        if (!contact.getUser().getEmail().equals(userDetails.getUsername())) {
            throw new EntityNotFoundException(INVALID_ACCESS);
        }
        contactService.editContact(contactDto.firstName, contactDto.lastName, contactDto.description, contactDto.id);
    }

    @DeleteMapping("/{id}")
    public void removeById(Authentication auth, @PathVariable int id) {
        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        Contact contact = contactService.getById(id);
        if (!contact.getUser().getEmail().equals(userDetails.getUsername())) {
            throw new EntityNotFoundException(INVALID_ACCESS);
        }
        contactService.removeById(id);
    }

    @GetMapping("")
    public List<ContactDto> requestAllContactsByUserEmail(Authentication auth) {
        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        return contactService.getAllContactsByUserId(userDetails.getUsername()).stream()
                .map(contactMapper::mapContactToDto).collect(Collectors.toList());
    }

    @PutMapping("/profile")
    public void editProfile(Authentication auth, @Valid @RequestBody ContactDto contactDto) {
        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        User user = userService.getUserByEmail(userDetails.getUsername());
        contactService.editContact(contactDto.firstName, contactDto.lastName, contactDto.description, user.getMyProfile().getId());
    }

    @GetMapping("/profile")
    public ContactDto getProfileById(Authentication auth) {
        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        User user = userService.getUserByEmail(userDetails.getUsername());
        return contactMapper.mapContactToDtoFull(contactService.getById(user.getMyProfile().getId()));
    }

}
