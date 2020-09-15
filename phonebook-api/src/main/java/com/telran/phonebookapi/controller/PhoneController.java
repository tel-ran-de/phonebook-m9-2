package com.telran.phonebookapi.controller;

import com.telran.phonebookapi.dto.PhoneDto;
import com.telran.phonebookapi.mapper.PhoneMapper;
import com.telran.phonebookapi.service.PhoneService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/phone")
public class PhoneController {

    PhoneService phoneService;
    PhoneMapper phoneMapper;

    public PhoneController(PhoneService phoneService, PhoneMapper phoneMapper) {
        this.phoneService = phoneService;
        this.phoneMapper = phoneMapper;
    }

    @PostMapping("")
    public void addPhone(@RequestBody @Valid PhoneDto phoneDto) {
        phoneService.add(phoneDto.contactId, phoneDto.countryCode, phoneDto.phoneNumber);
    }

    @PutMapping("")
    public void editPhone(@RequestBody @Valid PhoneDto phoneDto) {
        phoneService.editAllFields(phoneDto.contactId, phoneDto.countryCode, phoneDto.phoneNumber);
    }

    @GetMapping("/{id}")
    public PhoneDto getById(@PathVariable int id) {
        return phoneMapper.mapPhoneToDto(phoneService.getById(id));
    }

    @DeleteMapping("/{id}")
    public void removeById(@PathVariable int id) {
        phoneService.removeById(id);
    }

    @GetMapping("/{contactId}")
    public List<PhoneDto> getAllPhoneNumbers(@PathVariable int contactId) {
        return phoneService.getAllPhoneNumbersByContactId(contactId).stream()
                .map(number -> new PhoneDto(
                        number.getId(),
                        number.getCountryCode(),
                        number.getPhoneNumber(),
                        number.getContact().getId()
                )).collect(Collectors.toList());
    }
}
