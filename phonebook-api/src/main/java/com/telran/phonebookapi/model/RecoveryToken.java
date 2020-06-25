package com.telran.phonebookapi.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
@NoArgsConstructor
@Getter
public class RecoveryToken {

    @Id
    private String id;

    @OneToOne
    private User user;

    public RecoveryToken(String id, User user) {
        this.id = id;
        this.user = user;
    }
}
