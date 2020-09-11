package com.telran.phonebookapi.controller;

import com.telran.phonebookapi.dto.EmailDto;
import com.telran.phonebookapi.mapper.EmailMapper;
import com.telran.phonebookapi.model.Email;
import com.telran.phonebookapi.service.EmailService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/email")
public class EmailController {

    EmailService emailService;
    EmailMapper emailMapper;


    public EmailController(EmailService emailService,
                           EmailMapper emailMapper) {
        this.emailService = emailService;
        this.emailMapper = emailMapper;
    }

    @PostMapping("")
    public void addEmail(@RequestBody @Valid EmailDto emailDto) {
        emailService.add(emailDto.email, emailDto.contactId);
    }

    @PutMapping("")
    public void editEmail(@RequestBody @Valid EmailDto emailDto) {
        emailService.edit(emailDto.email, emailDto.id);
    }

    @GetMapping("/{id}")
    public EmailDto getById(@PathVariable int id) {

        return emailMapper.mapEmailToDto(emailService.getById(id));
    }

    @DeleteMapping("/{id}")
    public void removeById(@PathVariable int id) {
        emailService.removeById(id);
    }

    @GetMapping("/{contactId}")
    public List<EmailDto> getAllEmails(@PathVariable int contactId) {
        List<Email> emailList = new ArrayList<>(emailService.getAllEmailsByContactId(contactId));
        List<EmailDto> listDto = new ArrayList<>();
        for (Email emails : emailList) {
            EmailDto emailDto = emailMapper.mapEmailToDto(emails);
            listDto.add(emailDto);
        }
        return listDto;
    }
}
