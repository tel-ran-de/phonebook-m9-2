package com.telran.phonebookapi.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@NoArgsConstructor
@Getter
public class Phone {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Setter
    private long phoneNumber;
    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Contact contact;
    @Setter
    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private CountryCode countryCode;

    public Phone(Contact contact) {
        this.contact = contact;
    }

    public Phone(long phoneNumber, Contact contact, CountryCode countryCode) {
        this.phoneNumber = phoneNumber;
        this.contact = contact;
        this.countryCode =countryCode;
    }
}
