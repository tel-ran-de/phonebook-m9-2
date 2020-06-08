package com.telran.phonebookapi.model;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;

@Entity
@NoArgsConstructor
@Getter
public class Contact {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected String id;
    @Setter
    protected String firstName;
    @Setter
    protected String lastName;

    @OneToMany(mappedBy = "contact", cascade = CascadeType.REMOVE)
    ArrayList<String> emails = new ArrayList<>();
    @OneToMany(mappedBy = "contact", cascade = CascadeType.REMOVE)
    ArrayList<Phone> phones = new ArrayList<>();

    public Contact(String firstName,
                   String lastName,
                   ArrayList<String> emails,
                   ArrayList<Phone> phones) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.emails = emails;
        this.phones = phones;
    }

    @Override
    public String toString() {
        return "Person: "
                + firstName + " "
                + lastName + " "
                + emails + " "
                + phones;
    }

}

