package com.telran.phonebookapi.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@NoArgsConstructor
@AllArgsConstructor
public class PhoneDto {

    public int id;
    @NotBlank
    public int countryCode;
    @NotBlank
    public long phoneNumber;

    public int contactId;
}
