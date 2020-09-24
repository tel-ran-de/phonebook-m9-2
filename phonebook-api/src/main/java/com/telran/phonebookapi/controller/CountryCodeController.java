package com.telran.phonebookapi.controller;

import com.telran.phonebookapi.dto.CountryCodeDto;
import com.telran.phonebookapi.mapper.CountryCodeMapper;
import com.telran.phonebookapi.service.CountryCodeService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/country_code")
public class CountryCodeController {

    CountryCodeService countryCodeService;
    CountryCodeMapper countryCodeMapper;

    public CountryCodeController(CountryCodeService countryCodeService, CountryCodeMapper countryCodeMapper) {
        this.countryCodeService = countryCodeService;
        this.countryCodeMapper = countryCodeMapper;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("")
    public void addCode(@RequestBody @Valid CountryCodeDto codeDto) {
        countryCodeService.add(codeDto.code, codeDto.country);
    }

    @GetMapping("/{id}")
    public CountryCodeDto getById(@PathVariable int id) {
        return countryCodeMapper.mapCountryCodeToDto(countryCodeService.getById(id));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public void removeById(@PathVariable int id) {
        countryCodeService.removeById(id);
    }

    @GetMapping("")
    public List<CountryCodeDto> getAllCountryCodes() {
        return countryCodeService.getAllCountryCodes().stream()
                .map(countryCodeMapper::mapCountryCodeToDto)
                .collect(Collectors.toList());
    }
}
