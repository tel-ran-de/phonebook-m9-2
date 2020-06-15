package com.telran.phonebookapi.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class EmailSenderTest {

    @InjectMocks
    EmailSender emailSender;

    @Mock
    SimpleMailMessage mailMessage;

    @Mock
    JavaMailSender javaMailSender;

    @Test
    public void testSendMail_mailSender_sendsEmail() {

        String toEmail = "janedoe@example.com";
        String subject = "Test subject";
        String message = "Test text";

        mailMessage.setTo(toEmail);
        mailMessage.setSubject(subject);
        mailMessage.setText(message);

        mailMessage.setFrom("johndoe@example.com");

        javaMailSender.send(mailMessage);

        assertEquals("janedoe@example.com", mailMessage.getTo());
        assertEquals("Test subject", mailMessage.getSubject());
        assertEquals("Test text", mailMessage.getText());
    }
}
