package com.telran.phonebookapi.dto;

import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
public class ContactDto {

    public int id;

    @NotBlank
    public String firstName;

    public String lastName;

    public String description;

    public String userId;

    public List<PhoneDto> phoneNumbers = new ArrayList<>();

    public List<AddressDto> addresses = new ArrayList<>();

    public List<String> emails = new ArrayList<>();

    public ContactDto(@NotBlank String firstName, String lastName, String description) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.description = description;
    }
}
