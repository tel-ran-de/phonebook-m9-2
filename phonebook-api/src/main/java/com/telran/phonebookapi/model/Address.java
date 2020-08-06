package com.telran.phonebookapi.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Setter
    private String street;
    @Setter
    private String zip;
    @Setter
    private String city;
    @Setter
    private String country;
    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Contact contact;

    public Address(Contact contact) {
        this.contact = contact;
    }

    public Address(String zip, String country, String city, String street, Contact contact) {
        this.street = street;
        this.zip = zip;
        this.city = city;
        this.country = country;
        this.contact =contact;
    }
}
