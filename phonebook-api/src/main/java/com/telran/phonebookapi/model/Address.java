package com.telran.phonebookapi.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @Setter
    private String street;
    @Setter
    private String house;
    @Setter
    private String zip;
    @Setter
    private String city;
    @Setter
    private String country;

    public Address(String street, String house, String zip, String city, String country) {
        this.street = street;
        this.house = house;
        this.zip = zip;
        this.city = city;
        this.country = country;
    }
}
