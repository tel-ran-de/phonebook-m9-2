package com.telran.phonebookapi.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter
public class Token {

    @Id
    private String uuid;
    private String email;

    public Token(String uuid, String email) {
        this.uuid = uuid;
        this.email = email;
    }
}


