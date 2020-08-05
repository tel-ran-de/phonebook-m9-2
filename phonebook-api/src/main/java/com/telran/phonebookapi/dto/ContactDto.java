package com.telran.phonebookapi.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
public class ContactDto {

    public int id;

    public String firstName;

    public String lastName;

    public String description;

    public String userId;

    public List<PhoneDto> phoneNumbers = new ArrayList<>();

    public List<AddressDto> addresses = new ArrayList<>();

    public List<String> emails = new ArrayList<>();

    public ContactDto(int id, String firstName, String lastName, String description, String userId) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.description = description;
        this.userId = userId;
    }
}
