package com.telran.phonebookapi.service;

import com.telran.phonebookapi.exception.CodeAlreadyExistsException;
import com.telran.phonebookapi.model.CountryCode;
import com.telran.phonebookapi.persistance.ICountryCodeRepository;

import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;


@Service
public class CountryCodeService {

    static final String CODE_EXIST = "Error! This country code has already existed in our DB";
    static final String CODE_DOES_NOT_EXIST = "Error! This country code doesn't exist in our DB";

    ICountryCodeRepository countryCodeRepository;

    public CountryCodeService(ICountryCodeRepository countryCodeRepository) {
        this.countryCodeRepository = countryCodeRepository;
    }

    public void add(int id, String code, String country) {
        if (countryCodeRepository.findById(id).isPresent()) {
            throw new CodeAlreadyExistsException(CODE_EXIST);
        } else {
            CountryCode newCode = new CountryCode(code, country);
            countryCodeRepository.save(newCode);
        }
    }

    public void editAllFields(int id, String code, String country) {
        CountryCode countryCode = countryCodeRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(CODE_DOES_NOT_EXIST));
        countryCode.setCode(code);
        countryCode.setCountry(country);
        countryCodeRepository.save(countryCode);
    }

    public CountryCode getById(int id) {
        return countryCodeRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(CODE_DOES_NOT_EXIST));
    }

    public void removeById(int id) {
        countryCodeRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(CODE_DOES_NOT_EXIST));
        countryCodeRepository.deleteById(id);
    }

    public List<CountryCode> getAllCountryCodes() {
        return countryCodeRepository.findAll();
    }
}
