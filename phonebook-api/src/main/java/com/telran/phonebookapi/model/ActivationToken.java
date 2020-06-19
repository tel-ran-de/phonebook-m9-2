package com.telran.phonebookapi.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class ActivationToken {

    @Id
    private String uuid;
    @OneToOne
    private User user;

    public ActivationToken(String uuid, User user) {
        this.uuid = uuid;
        this.user = user;
    }
}


