package com.telran.phonebookapi.mapper;

import com.telran.phonebookapi.dto.CountryCodeDto;
import com.telran.phonebookapi.model.CountryCode;
import org.springframework.stereotype.Component;

@Component
public class CountryCodeMapper {

    public CountryCodeDto mapCountryCodeToDto(CountryCode countryCode) {
        return new CountryCodeDto(countryCode.getCode(), countryCode.getCountry());
    }
}
