package com.telran.phonebookapi.controller;

import com.telran.phonebookapi.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MyController {

    @Autowired
    private EmailService emailService;

    @GetMapping(value = "/sendmail")
    public String sendmail() {

        emailService.sendMail("innysik@gmail.com", "Test Subject", "Test mail");

        return "emailsent";
    }
}
