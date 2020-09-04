package com.telran.phonebookapi.controller;

import com.telran.phonebookapi.dto.EmailDto;
import com.telran.phonebookapi.service.EmailService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/email")
public class EmailController {

    EmailService emailService;

    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping("")
    public void addEmail(@RequestBody @Valid EmailDto emailDto) {
        emailService.add(emailDto);
    }

    @PutMapping("")
    public void editEmail(@RequestBody @Valid EmailDto emailDto) {
        emailService.edit(emailDto);
    }

    @GetMapping("/{id}")
    public EmailDto getById(@PathVariable int id) {
        return emailService.getById(id);
    }

    @DeleteMapping("/{id}")
    public void removeById(@PathVariable int id) {
        emailService.removeById(id);
    }

    @GetMapping("/{contactId}/all")
    public List<EmailDto> getAllEmails(@PathVariable int contactId) {
        return emailService.getAllEmailsByContactId(contactId);
    }
}
