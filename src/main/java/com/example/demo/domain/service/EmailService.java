package com.example.demo.domain.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendVerificationEmail(String to, String token) {
        SimpleMailMessage message = new SimpleMailMessage();

        message.setFrom("ebefu24@gmail.com");
        message.setTo(to);
        message.setSubject("Verifica tu cuenta");

        String verificationUrl = "http://localhost:8080/api/v1/users/verify?token=" + token;

        message.setText(
                "Pulsa el siguiente enlace para verificar tu cuenta:\n"
                        + verificationUrl
        );

        mailSender.send(message);
    }
}
