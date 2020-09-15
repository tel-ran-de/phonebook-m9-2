package com.telran.phonebookapi.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class PhoneDto {

    public int id;

    public int countryCode;

    public long phoneNumber;

    public int contactId;
}
