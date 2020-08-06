package com.telran.phonebookapi.mapper;

import com.telran.phonebookapi.dto.PhoneDto;
import com.telran.phonebookapi.model.Phone;
import org.springframework.stereotype.Component;

@Component
public class PhoneMapper {

    public PhoneDto mapPhoneToDto(Phone phone) {
        return new PhoneDto(phone.getId(), phone.getCountryCode(), phone.getPhoneNumber(), phone.getContact().getId());
    }
}
