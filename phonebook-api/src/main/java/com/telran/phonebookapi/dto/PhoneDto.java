package com.telran.phonebookapi.dto;

import com.telran.phonebookapi.validation.annotation.ValidPhoneNumber;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@ValidPhoneNumber(maxValue = 16, minValue = 9)
public class PhoneDto {

    public int id;

    public int countryCode;
    //@Value("^\\d{10||11||12||13||14||15}$")
    public long phoneNumber;

    public int contactId;
}
