package com.telran.phonebookapi.mapper;

import com.telran.phonebookapi.dto.PhoneDto;
import com.telran.phonebookapi.model.Contact;
import com.telran.phonebookapi.model.CountryCode;
import com.telran.phonebookapi.model.Phone;
import com.telran.phonebookapi.model.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PhoneMapperTest {

    PhoneMapper phoneMapper = new PhoneMapper();

    @Test
    void PhoneNumberDto() {
        User user = new User("test@gmail.com", "112233");
        Contact contact = new Contact("Name", user);
        CountryCode code = new CountryCode("+49", "Germany");
        Phone number = new Phone(12345678, contact, code);

        PhoneDto phoneNumberDto = new PhoneDto(1, "+49", 12345678, 1);

        PhoneDto phoneNumberDtoMapped = phoneMapper.mapPhoneToDto(number);
        assertEquals(phoneNumberDto.phoneNumber, phoneNumberDtoMapped.phoneNumber);
        assertEquals(phoneNumberDto.countryCode, phoneNumberDtoMapped.countryCode);
    }
}
