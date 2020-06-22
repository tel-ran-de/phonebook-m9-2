package com.telran.phonebookapi.service;

//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.mail.SimpleMailMessage;
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.stereotype.Component;

//@Component
//public class EmailSender {
//
//    @Value("${phonebook-m9-2.email-from}")
//    String fromMail;
//
//    private JavaMailSender javaMailSender;
//
//    public EmailSender(JavaMailSender javaMailSender) {
//        this.javaMailSender = javaMailSender;
//    }
//
//    public void sendMail(String toEmail, String subject, String message) {
//
//        SimpleMailMessage mailMessage = new SimpleMailMessage();
//        mailMessage.setTo(toEmail);
//        mailMessage.setSubject(subject);
//        mailMessage.setText(message);
//
//        mailMessage.setFrom(fromMail);
//
//        javaMailSender.send(mailMessage);
//
//    }
//}
