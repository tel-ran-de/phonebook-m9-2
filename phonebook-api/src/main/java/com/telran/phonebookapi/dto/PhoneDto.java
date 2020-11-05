package com.telran.phonebookapi.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;

@NoArgsConstructor
@AllArgsConstructor
public class PhoneDto {

    public int id;

    public int countryCode;

    @Size(min = 9, max = 15)
    public long phoneNumber;

    public int contactId;
}
