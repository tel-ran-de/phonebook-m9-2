package com.telran.phonebookapi.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
public class Phone {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    @Setter
    private int countryCode;
    @Setter
    private int phoneNumber;
    @Setter
    private Contact contact;

    public Phone(int countryCode, int phoneNumber, Contact contact) {
        this.countryCode = countryCode;
        this.phoneNumber = phoneNumber;
        this.contact = contact;
    }

    //  maxLengthOfPhoneNumber?
}
