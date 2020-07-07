package com.telran.phonebookapi.controller;

import com.telran.phonebookapi.dto.AddressDto;
import com.telran.phonebookapi.service.AddressService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class AddressController {

    AddressService addressService;

    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @PostMapping("api/contact/{contactId}/address")
    public void addAddress(@RequestBody AddressDto addressDto, @PathVariable int contactId) {
        addressDto.contactId = contactId;
        addressService.add(addressDto);
    }

    @PutMapping("api/address")
    public void editPhone(@RequestBody @Valid AddressDto addressDto) {
        addressService.editCity(addressDto);
    }

    @GetMapping("api/contact/{contactId}/address")
    public List<AddressDto> getAddressesByContactId(@PathVariable int contactId) {
        return addressService.getByContactId(contactId);
    }

    @DeleteMapping("api/address/{id}")
    public void removeById(@PathVariable int id) {
        addressService.removeById(id);
    }

}
