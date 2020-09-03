package com.telran.phonebookapi.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
public class Contact {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Setter
    private String firstName;
    @Setter
    private String lastName;
    @Setter
    private String description;
    @Setter
    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    @OneToMany(mappedBy = "contact", cascade = CascadeType.REMOVE)
    private final List<Email> emails = new ArrayList<>();

    @OneToMany(mappedBy = "contact", cascade = CascadeType.REMOVE)
    private final List<Phone> phones = new ArrayList<>();

    @OneToMany(mappedBy = "contact", cascade = CascadeType.REMOVE)
    private final List<Address> addresses = new ArrayList<>();

    public Contact(String firstName, User user) {
        this.firstName = firstName;
        this.user = user;
    }

    public void addAddress(Address address) {
        addresses.add(address);
    }

    public void addPhone(Phone phone) {
        phones.add(phone);
    }

    public void addEmail(Email email) {
        emails.add(email);
    }

    public List<Phone> getPhoneNumbers() {
        return Collections.unmodifiableList(phones);
    }

    public List<Address> getAddresses() {
        return Collections.unmodifiableList(addresses);
    }

    public List<Email> getEmails() {
        return Collections.unmodifiableList(emails);
    }

}

