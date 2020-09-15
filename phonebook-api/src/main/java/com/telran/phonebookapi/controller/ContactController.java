package com.telran.phonebookapi.controller;

import com.telran.phonebookapi.dto.ContactDto;
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
@RequestMapping("/api/contact")
public class ContactController {

    ContactService contactService;
    ContactMapper contactMapper;
    AddressMapper addressMapper;
    PhoneMapper phoneMapper;
    EmailMapper emailMapper;

    public ContactController(ContactService contactService, ContactMapper contactMapper, AddressMapper addressMapper, PhoneMapper phoneMapper, EmailMapper emailMapper) {
        this.contactService = contactService;
        this.contactMapper = contactMapper;
        this.addressMapper = addressMapper;
        this.phoneMapper = phoneMapper;
        this.emailMapper = emailMapper;
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

    @GetMapping("/{email}/all")
    public List<ContactDto> requestAllContactsByUserEmail(@PathVariable String email) {
        return contactService.getAllContactsByUserId(email).stream()
                .map(contact -> new ContactDto(
                        contact.getId(),
                        contact.getFirstName(),
                        contact.getLastName(),
                        contact.getDescription(),
                        contact.getUser().getEmail(),
                        phoneMapper.mapListPhoneToDto(contact.getPhoneNumbers()),
                        addressMapper.mapListAddressToDto(contact.getAddresses()),
                        emailMapper.mapListEmailToDto(contact.getEmails())))
//                    return ContactDto.builder()
//                            .id(contact.getId())
//                            .firstName(contact.getFirstName())
//                            .lastName(contact.getLastName())
//                            .description(contact.getDescription())
//                            .userId(contact.getUser().getEmail())
//                            .phoneNumbers(phoneMapper.mapListPhoneToDto(contact.getPhones()))
//                            .addresses(addressMapper.mapListAddressToDto(contact.getAddresses()))
//                            .emails(emailMapper.mapListEmailToDto(contact.getEmails()));
                .collect(Collectors.toList());
    }

    @PostMapping("/profile")
    public void addProfile(@Valid @RequestBody ContactDto contactDto) {
        contactService.addProfile(contactDto.firstName, contactDto.lastName, contactDto.description, contactDto.userId);
    }

}
