package com.telran.phonebookapi.controller;

import com.telran.phonebookapi.dto.AddressDto;
import com.telran.phonebookapi.mapper.AddressMapper;
import com.telran.phonebookapi.model.Address;
import com.telran.phonebookapi.model.Contact;
import com.telran.phonebookapi.service.AddressService;
import com.telran.phonebookapi.service.ContactService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/address")
public class AddressController {

    static final String INVALID_ACCESS = "Error! You have no permission";

    AddressService addressService;
    ContactService contactService;
    AddressMapper addressMapper;

    public AddressController(AddressService addressService, ContactService contactService, AddressMapper addressMapper) {
        this.addressService = addressService;
        this.contactService = contactService;
        this.addressMapper = addressMapper;
    }

    @PostMapping("")
    public void addAddress(Authentication auth, @RequestBody @Valid AddressDto addressDto) {
        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        Contact contact = contactService.getById(addressDto.contactId);
        if (!contact.getUser().getEmail().equals(userDetails.getUsername())) {
            throw new EntityNotFoundException(INVALID_ACCESS);
        }
        addressService.add(addressDto.contactId,
                addressDto.zip,
                addressDto.country,
                addressDto.city,
                addressDto.street);
    }

    @PutMapping("")
    public void editAddress(Authentication auth, @RequestBody @Valid AddressDto addressDto) {
        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        Contact contact = contactService.getById(addressDto.contactId);
        if (!contact.getUser().getEmail().equals(userDetails.getUsername())) {
            throw new EntityNotFoundException(INVALID_ACCESS);
        }
        addressService.editAllFields(addressDto.id,
                addressDto.zip,
                addressDto.country,
                addressDto.city,
                addressDto.street);
    }

    @GetMapping("/{id}")
    public AddressDto getById(Authentication auth, @PathVariable int id) {
        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        Address address = addressService.getById(id);
        Contact contact = contactService.getById(address.getContact().getId());
        if (!contact.getUser().getEmail().equals(userDetails.getUsername())) {
            throw new EntityNotFoundException(INVALID_ACCESS);
        }
        return addressMapper.mapAddressToDto(addressService.getById(id));
    }

    @DeleteMapping("/{id}")
    public void removeById(Authentication auth, @PathVariable int id) {
        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        Address address = addressService.getById(id);
        Contact contact = contactService.getById(address.getContact().getId());
        if (!contact.getUser().getEmail().equals(userDetails.getUsername())) {
            throw new EntityNotFoundException(INVALID_ACCESS);
        }
        addressService.removeById(id);
    }

}
