package com.telran.phonebookapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@SpringBootApplication(scanBasePackages = "com.telran.phonebookapi")
public class PhonebookApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(PhonebookApiApplication.class, args);
    }

    @Bean // Temp workaround
    public JavaMailSender mailSender(){
        return new JavaMailSenderImpl();
    }
}
