package com.telran.phonebookapi.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.telran.phonebookapi.model.Contact;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class UserDto {

        public UserDto(String email,
                       String password) {
            this.email = email;
            this.password = password;

        }

        public String email;

        public String password;

        public Contact myProfile;

    }

