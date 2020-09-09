package com.telran.phonebookapi.controller;

import com.telran.phonebookapi.dto.ContactDto;
import com.telran.phonebookapi.dto.UserDto;
import com.telran.phonebookapi.dto.UserEmailDto;
import com.telran.phonebookapi.mapper.AddressMapper;
import com.telran.phonebookapi.mapper.ContactMapper;
import com.telran.phonebookapi.mapper.EmailMapper;
import com.telran.phonebookapi.mapper.PhoneMapper;
import com.telran.phonebookapi.service.ContactService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
@RequestMapping("/api/contact")
public class ContactController {

    ContactService contactService;
    ContactMapper contactMapper;
    AddressMapper addressMapper;
    PhoneMapper phoneMapper;
    EmailMapper emailMapper;

    public ContactController(ContactService contactService, ContactMapper contactMapper, AddressMapper addressMapper, PhoneMapper phoneMapper, EmailMapper emailMapper) {
        this.contactService = contactService;
        this.emailMapper = emailMapper;
        this.addressMapper = addressMapper;
        this.phoneMapper = phoneMapper;
        this.contactMapper = contactMapper;
    }

    @PostMapping("")
    public void addContact(@Valid @RequestBody ContactDto contactDto) {
        contactService.add(contactDto);
    }

    @GetMapping("/{id}")
    public ContactDto getById(@PathVariable int id) {
        return contactMapper.mapContactToDto(contactService.getById(id));
    }

    @GetMapping("/{id}/extended")
    public ContactDto getByIdFullDetails(@PathVariable int id) {
        return contactMapper.mapContactToDto(contactService.getByIdFullDetails(id));
    }

    @PutMapping("")
    public void editContact(@Valid @RequestBody ContactDto contactDto) {
        contactService.editAllFields(contactDto);
    }

    @DeleteMapping("/{id}")
    public void removeById(@PathVariable int id) {
        contactService.removeById(id);
    }

    @PostMapping("/all")
    public List<ContactDto> requestAllContactsByUserEmail(@Valid @RequestBody UserDto userDto) {

        return contactService.getAllContactsByUserId(userDto).stream()
                .map(contact -> {
                    ContactDto contactDto = new ContactDto(contact.getId(), contact.getFirstName(), contact.getLastName(), contact.getDescription(), contact.getUser().getEmail(), phoneMapper.mapListPhoneToDto(contact.getPhones()), addressMapper.mapListAddressToDto(contact.getAddresses()), emailMapper.mapListEmailToDto(contact.getEmails()));
//                    return ContactDto.builder()
//                            .id(contact.getId())
//                            .firstName(contact.getFirstName())
//                            .lastName(contact.getLastName())
//                            .description(contact.getDescription())
//                            .userId(contact.getUser().getEmail())
//                            .phoneNumbers(phoneMapper.mapListPhoneToDto(contact.getPhones()))
//                            .addresses(addressMapper.mapListAddressToDto(contact.getAddresses()))
//                            .emails(emailMapper.mapListEmailToDto(contact.getEmails()));
                    return contactDto;
                })
                .collect(Collectors.toList());

    }

    @PostMapping("/profile")
    public void addProfile(@Valid @RequestBody ContactDto contactDto) {
        contactService.addProfile(contactDto);
    }

    @PutMapping("/profile")
    public void editProfile(@Valid @RequestBody ContactDto contactDto) {
        contactService.editProfile(contactDto);
    }

    @PostMapping("/get-profile")
    public ContactDto getProfile(@Valid @RequestBody UserEmailDto userEmailDto) {
        return contactMapper.mapContactToDto(contactService.getProfile(userEmailDto));
    }

}
