package com.telran.phonebookapi.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.telran.phonebookapi.model.Contact;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

@NoArgsConstructor
public class UserDto {

    public UserDto(String email,
                   String password) {
        this.email = email;
        this.password = password;

    }

    @Email(message = "Please, check entered email is correct")
    public String email;

    @Size(max = 20, min = 8, message = "The password is shorter than {min} or longer than {max}")
    public String password;

    public Contact myProfile;

}
