package com.telran.phonebookapi.mapper;

import com.telran.phonebookapi.dto.EmailDto;
import com.telran.phonebookapi.model.Email;
import org.springframework.stereotype.Component;

@Component
public class EmailMapper {

    public EmailDto mapEmailToDto(Email email) {
        return new EmailDto(email.getId(), email.getEmail(), email.getContact().getId());
    }
}
