package com.telran.phonebookapi.controller;

import com.telran.phonebookapi.dto.PhoneDto;
import com.telran.phonebookapi.service.PhoneService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/phone")
public class PhoneController {

    PhoneService phoneService;

    public PhoneController(PhoneService phoneService) {
        this.phoneService = phoneService;
    }

    @PostMapping("")
    public void addPhone(@RequestBody @Valid PhoneDto phoneDto) {
        phoneService.add(phoneDto);
    }

    @PutMapping("")
    public void editPhone(@RequestBody @Valid PhoneDto phoneDto) {
        phoneService.editAllFields(phoneDto);
    }

    @GetMapping("/{id}")
    public PhoneDto getById(@PathVariable int id) {
        return phoneService.getById(id);
    }

    @DeleteMapping("/{id}")
    public void removeById(@PathVariable int id) {
        phoneService.removeById(id);
    }

    @GetMapping("/{contactId}/all")
    public List<PhoneDto> getAllPhoneNumbers(@PathVariable int contactId) {
        return phoneService.getAllPhoneNumbersByContactId(contactId);
    }
}
