package com.telran.phonebookapi.model;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
public class CountryCode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Setter
    private String code;
    @Setter
    private String country;
    @OneToMany(mappedBy = "countryCode", cascade = CascadeType.REMOVE)
    private final List<Phone> numbers = new ArrayList<>();

    public CountryCode(String code, String country) {
        this.code = code;
        this.country = country;
    }

    public CountryCode(int id, String code, String country) {
        this.id = id;
        this.code = code;
        this.country = country;
    }
}
