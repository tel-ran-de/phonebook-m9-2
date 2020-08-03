package com.telran.phonebookapi.dto;

import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
public class UserDto {

    public UserDto(String email,
                   String password) {
        this.email = email;
        this.password = password;

    }

    @Email(message = "Please, check entered email is correct",
            regexp = "^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,10}$")
    @NotBlank
    public String email;

    @Size(max = 20, min = 8, message = "The password is shorter than {min} or longer than {max}")
    @NotBlank
    public String password;

    public List<ContactDto> contactDtos = new ArrayList<>();

}
