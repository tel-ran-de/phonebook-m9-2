package com.telran.phonebookapi.service;

import com.telran.phonebookapi.model.CountryCode;
import com.telran.phonebookapi.persistance.ICountryCodeRepository;

import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;


@Service
public class CountryCodeService {

    static final String CODE_DOES_NOT_EXIST = "Error! This country code doesn't exist in our DB";

    ICountryCodeRepository countryCodeRepository;

    public CountryCodeService(ICountryCodeRepository countryCodeRepository) {
        this.countryCodeRepository = countryCodeRepository;
    }

    public void add(int code, String country) {
        CountryCode newCode = new CountryCode(code, country);
        countryCodeRepository.save(newCode);
    }

    public CountryCode getById(int code) {
        return countryCodeRepository.findById(code).orElseThrow(() -> new EntityNotFoundException(CODE_DOES_NOT_EXIST));
    }

    public void removeById(int code) {
        countryCodeRepository.findById(code).orElseThrow(() -> new EntityNotFoundException(CODE_DOES_NOT_EXIST));
        countryCodeRepository.deleteById(code);
    }

    public List<CountryCode> getAllCountryCodes() {
        return countryCodeRepository.findAll();
    }
}
