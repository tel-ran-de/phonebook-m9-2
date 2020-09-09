package com.telran.phonebookapi.dto;

import com.telran.phonebookapi.model.PhoneCountryCode;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@NoArgsConstructor
@AllArgsConstructor
public class PhoneDto {

    public int id;
    @NotBlank
    public PhoneCountryCode countryCode;
    @NotBlank
    public long phoneNumber;

    public int contactId;
}
