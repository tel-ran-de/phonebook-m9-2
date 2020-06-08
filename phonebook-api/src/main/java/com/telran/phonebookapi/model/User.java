package com.telran.phonebookapi.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;

@Entity
@NoArgsConstructor
@Getter
public class User {

    @Setter
    private String email;
    @Setter
    private String password;
    @Setter
    private Contact myProfile;

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private ArrayList<Contact> contacts = new ArrayList<>();

    public User(String email, String password, Contact myProfile) {
        this.email = email;
        this.password = password;
        this.myProfile = myProfile;
    }

    public void add(Contact contact) {
        contacts.add(contact);
        System.out.println("Added contact: " + contact);
    }

    public void delete(Contact contact) {
        contacts.remove(contact);
        System.out.println("Deleted contact: " + contact);
    }

    ArrayList<Contact> getContacts() {
        return contacts;
    }

    ArrayList<Contact> getContacts(String query) {
        ArrayList<Contact> contacts = new ArrayList<>();

        for (Contact contact : this.contacts) {
            if (contact.firstName.contains(query) || contact.lastName.contains(query)) {
                contacts.add(contact);
            }
        }
        return contacts;
    }

}
