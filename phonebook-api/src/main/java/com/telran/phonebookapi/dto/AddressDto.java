package com.telran.phonebookapi.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;

@NoArgsConstructor
@AllArgsConstructor
public class AddressDto {

    public int id;
    @Size(max = 8, min = 3, message = "The zip-code is shorter than {min} or longer than {max}" )
    public String zip;

    public String country;

    public String city;

    public String street;

    public int contactId;

}
