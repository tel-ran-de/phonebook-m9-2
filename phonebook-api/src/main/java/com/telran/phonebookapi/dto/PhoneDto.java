package com.telran.phonebookapi.dto;

import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@NoArgsConstructor
public class PhoneDto {

    public int id;
    @NotBlank
    public int countryCode;
    @NotBlank
    public int phoneNumber;

    public int contactId;
}
