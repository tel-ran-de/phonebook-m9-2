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
    private String id;
    @Setter
    private String firstName;
    @Setter
    private String lastName;
    @Setter
    private String description;

    @ElementCollection
    ArrayList<String> emails = new ArrayList<>();

    @OneToMany(mappedBy = "contact", cascade = CascadeType.REMOVE)
    ArrayList<Phone> phones = new ArrayList<>();

    @OneToMany(mappedBy = "contact", cascade = CascadeType.REMOVE)
    ArrayList<Address> addresses = new ArrayList<>();

    public Contact(String firstName) {
        this.firstName = firstName;

    }

}

