package com.telran.phonebookapi.controller;

import com.telran.phonebookapi.dto.PhoneDto;
import com.telran.phonebookapi.service.PhoneService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class PhoneController {

    PhoneService phoneService;

    public PhoneController(PhoneService phoneService) {
        this.phoneService = phoneService;
    }

    @PostMapping("api/contact/{contactId}/phone")
    public void addPhone(@RequestBody PhoneDto phoneDto, @PathVariable int contactId) {
        phoneDto.contactId = contactId;
        phoneService.add(phoneDto);
    }

    @PutMapping("api/phone")
    public void editPhone(@RequestBody @Valid PhoneDto phoneDto) {
        phoneService.edit(phoneDto);
    }

    @GetMapping("api/contact/{contactId}/phone")
    public List<PhoneDto> getPhoneNumbersByContactId(@PathVariable int contactId) {
        return phoneService.getByContactId(contactId);
    }

    @DeleteMapping("api/phone/{id}")
    public void removeById(@PathVariable int id) {
        phoneService.removeById(id);
    }

}
