package com.telran.phonebookapi;

import com.telran.phonebookapi.service.EmailService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
class PhonebookApiApplicationTests {

    @Autowired
    private EmailService emailService;

    @Test
    public void testEmail() {
        emailService.sendMail("innysik@gmail.com", "Test subject", "Test mail");
    }

}
