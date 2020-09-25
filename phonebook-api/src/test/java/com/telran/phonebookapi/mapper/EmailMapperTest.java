package com.telran.phonebookapi.mapper;

import com.telran.phonebookapi.dto.EmailDto;
import com.telran.phonebookapi.model.Contact;
import com.telran.phonebookapi.model.Email;
import com.telran.phonebookapi.model.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EmailMapperTest {
    EmailMapper emailMapper = new EmailMapper();

    @Test
    public void EmailDto(){
        User user = new User("test@gmail.com", "112233");
        Contact contact = new Contact("Name", user);
        Email email = new Email("mail@mail.com",contact);

        EmailDto emailDto = new EmailDto(1,"mail@mail.com",1);

        EmailDto emailDtoMapped = emailMapper.mapEmailToDto(email);
        assertEquals(emailDto.email, emailDtoMapped.email);

    }

//    @Test
//    public void DtoToEmail(){
//        User user = new User("test@gmail.com", "112233");
//        Contact contact = new Contact("Name", user);
//        EmailDto emailDto = new EmailDto(0,"mail@mail.com",0);
//
//        Email emailFromDto = emailMapper.mapDtoToEmail(emailDto);
//        assertEquals("mail@mail.com", emailFromDto.getEmail());
//
//    }

}
