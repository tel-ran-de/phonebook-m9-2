package com.telran.phonebookapi.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
public class CountryCode {

    @Id
    private int code;
    @Setter
    private String country;

    public CountryCode(int code, String country) {
        this.code = code;
        this.country = country;
    }
}
