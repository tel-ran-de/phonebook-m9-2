package com.telran.phonebookapi.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class EmailSenderTest {

    @InjectMocks
    EmailSender emailSender;

    @Mock
    SimpleMailMessage mailMessage;

    @Mock
    JavaMailSender javaMailSender;

    @Captor
    ArgumentCaptor<SimpleMailMessage> messageCaptor;

    @Test
    public void testSendMail_mailSender_sendsEmail() {
        String toEmail = "janedoe@example.com";
        String subject = "Test subject";
        String message = "Test text";

        emailSender.sendMail(toEmail, subject, message);

        Mockito.verify(javaMailSender, times(1)).send(messageCaptor.capture());
        SimpleMailMessage capturedMessage = messageCaptor.getValue();

        assertEquals("janedoe@example.com", Objects.requireNonNull(capturedMessage.getTo())[0]);
        assertEquals("Test subject", capturedMessage.getSubject());
        assertEquals("Test text", capturedMessage.getText());
    }
}
