package com.telran.phonebookapi.dto;

import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@NoArgsConstructor
public class AddressDto {

    public int id;
    @NotBlank
    public String street;
    @NotBlank
    public String zip;
    @NotBlank
    public String city;
    @NotBlank
    public String country;

    public int contactId;
}
