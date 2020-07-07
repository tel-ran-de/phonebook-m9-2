package com.telran.phonebookapi.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.*;

@Entity(name = "Users")
@NoArgsConstructor
@Getter
public class User {
    @Id
    private String email;
    @Setter
    private String password;
    @Setter
    private boolean isActive;
    @OneToOne
    private Contact myProfile;

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<Contact> contacts = new ArrayList<>();

    @ElementCollection(fetch = FetchType.EAGER)
    private Set<UserRole> roles = new HashSet<>();

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public void addRole(UserRole role){
        roles.add(role);
    }

    public void removeRole(UserRole role){
        roles.remove(role);
    }

    public void addContact(Contact contact) {
        contacts.add(contact);
    }

    public List<Contact> getContacts() {
        return contacts;
    }
}
