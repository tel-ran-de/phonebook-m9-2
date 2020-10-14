package com.telran.phonebookapi.controller;

import com.telran.phonebookapi.dto.AddressDto;
import com.telran.phonebookapi.dto.ContactDto;
import com.telran.phonebookapi.dto.EmailDto;
import com.telran.phonebookapi.dto.PhoneDto;
import com.telran.phonebookapi.mapper.AddressMapper;
import com.telran.phonebookapi.mapper.ContactMapper;
import com.telran.phonebookapi.mapper.EmailMapper;
import com.telran.phonebookapi.mapper.PhoneMapper;
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
    PhoneMapper phoneMapper;
    AddressMapper addressMapper;
    EmailMapper emailMapper;

    public ContactController(UserService userService,
                             ContactService contactService,
                             ContactMapper contactMapper,
                             PhoneMapper phoneMapper,
                             AddressMapper addressMapper,
                             EmailMapper emailMapper) {
        this.userService = userService;
        this.contactService = contactService;
        this.contactMapper = contactMapper;
        this.phoneMapper = phoneMapper;
        this.addressMapper = addressMapper;
        this.emailMapper = emailMapper;
    }

    @PostMapping("")
    public ContactDto addContact(Authentication auth, @Valid @RequestBody ContactDto contactDto) {
        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        Contact contact =  contactService.add(userDetails.getUsername(), contactDto.firstName, contactDto.lastName, contactDto.description);
        return contactMapper.mapContactToDtoFull(contact);
    }

    @GetMapping("/{id}")
    public ContactDto getById(Authentication auth, @PathVariable int id) {
        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        Contact contact = contactService.getById(id);
        if (!contact.getUser().getEmail().equals(userDetails.getUsername())) {
            throw new EntityNotFoundException(INVALID_ACCESS);
        }
        return contactMapper.mapContactToDtoFull(contact);
    }

    @PutMapping("")
    public void editContact(Authentication auth, @Valid @RequestBody ContactDto contactDto) {
        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        Contact contact = contactService.getById(contactDto.id);
        if (!contact.getUser().getEmail().equals(userDetails.getUsername())) {
            throw new EntityNotFoundException(INVALID_ACCESS);
        }
        contactService.editContact(contactDto.id, contactDto.firstName, contactDto.lastName, contactDto.description);
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
        contactService.editContact(user.getMyProfile().getId(), contactDto.firstName, contactDto.lastName, contactDto.description);
    }

    @GetMapping("/profile")
    public ContactDto getProfileById(Authentication auth) {
        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        User user = userService.getUserByEmail(userDetails.getUsername());
        return contactMapper.mapContactToDtoFull(contactService.getById(user.getMyProfile().getId()));
    }

    @GetMapping("{id}/phones")
    public List<PhoneDto> requestAllPhonesByContactId(Authentication auth, @PathVariable int id) {
        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        Contact contact = contactService.getById(id);
        if (!contact.getUser().getEmail().equals(userDetails.getUsername())) {
            throw new EntityNotFoundException(INVALID_ACCESS);
        }
        return contact.getPhones().stream()
                .map(phoneMapper::mapPhoneToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("{id}/addresses")
    public List<AddressDto> requestAllAddressesByContactId(Authentication auth, @PathVariable int id) {
        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        Contact contact = contactService.getById(id);
        if (!contact.getUser().getEmail().equals(userDetails.getUsername())) {
            throw new EntityNotFoundException(INVALID_ACCESS);
        }
        return contact.getAddresses().stream()
                .map(addressMapper::mapAddressToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("{id}/emails")
    public List<EmailDto> requestAllEmailsByContactId(Authentication auth, @PathVariable int id) {
        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        Contact contact = contactService.getById(id);
        if (!contact.getUser().getEmail().equals(userDetails.getUsername())) {
            throw new EntityNotFoundException(INVALID_ACCESS);
        }
        return contact.getEmails().stream()
                .map(emailMapper::mapEmailToDto)
                .collect(Collectors.toList());
    }

}
