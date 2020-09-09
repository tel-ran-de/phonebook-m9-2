package com.telran.phonebookapi.mapper;

import com.telran.phonebookapi.dto.EmailDto;
import com.telran.phonebookapi.dto.PhoneDto;
import com.telran.phonebookapi.model.Contact;
import com.telran.phonebookapi.model.Email;
import com.telran.phonebookapi.model.Phone;
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

}
