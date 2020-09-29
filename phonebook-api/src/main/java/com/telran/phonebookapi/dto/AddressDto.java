package com.telran.phonebookapi.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class AddressDto {

    public int id;

    public String zip;

    public String country;

    public String city;

    public String street;

    public int contactId;

}
