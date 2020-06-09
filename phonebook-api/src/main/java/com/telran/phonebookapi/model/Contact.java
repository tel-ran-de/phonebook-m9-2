package com.telran.phonebookapi.model;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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
    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    @ElementCollection
    private List<String> emails = new ArrayList<>();

    @OneToMany(mappedBy = "contact", cascade = CascadeType.REMOVE)
    private List<Phone> phones = new ArrayList<>();

    @OneToMany(mappedBy = "contact", cascade = CascadeType.REMOVE)
    private List<Address> addresses = new ArrayList<>();

    public Contact(String firstName, User user) {
        this.firstName = firstName;
        this.user = user;

    }

    public void add(Address address) {
        addresses.add(address);
    }

    public void delete(Address address) {
        addresses.remove(address);
    }

    public void add(Phone phone) {
        phones.add(phone);
    }

    public void delete(Phone phone) {
        phones.remove(phone);
    }

    public void add(String email) {
        emails.add(email);
    }

    public void delete(String email) {
        emails.remove(email);
    }

}

