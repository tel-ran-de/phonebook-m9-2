package com.telran.phonebookapi.controller;

import com.telran.phonebookapi.dto.CountryCodeDto;
import com.telran.phonebookapi.mapper.CountryCodeMapper;
import com.telran.phonebookapi.service.CountryCodeService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
@RequestMapping("/api/code")
public class CountryCodeController {

    CountryCodeService countryCodeService;
    CountryCodeMapper countryCodeMapper;

    public CountryCodeController(CountryCodeService countryCodeService, CountryCodeMapper countryCodeMapper) {
        this.countryCodeService = countryCodeService;
        this.countryCodeMapper = countryCodeMapper;
    }

    @PostMapping("")
    public void addCode(@RequestBody @Valid CountryCodeDto codeDto) {
        String code = codeDto.code;
        String country = codeDto.country;
        int id = codeDto.id;
        countryCodeService.add(id, code, country);
    }

    @PutMapping("")
    public void editCode(@RequestBody @Valid CountryCodeDto codeDto) {
        String code = codeDto.code;
        String country = codeDto.country;
        int id = codeDto.id;
        countryCodeService.editAllFields(id, code, country);
    }

    @GetMapping("/{id}")
    public CountryCodeDto getById(@PathVariable int id) {
        return countryCodeMapper.mapCountryCodeToDto(countryCodeService.getById(id));
    }

    @DeleteMapping("/{id}")
    public void removeById(@PathVariable int id) {
        countryCodeService.removeById(id);
    }

    @GetMapping("/all")
    public List<CountryCodeDto> getAllCountryCodes() {
        return countryCodeService.getAllCountryCodes().stream()
                .map(countryCodeMapper::mapCountryCodeToDto)
                .collect(Collectors.toList());
    }
}
