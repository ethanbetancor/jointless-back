package com.example.demo.domain.service;

import com.resend.Resend;
import com.resend.core.exception.ResendException;
import com.resend.services.emails.model.CreateEmailOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Value("${resend.api.key}")
    private String resendApiKey;

    public void sendVerificationEmail(String to, String token) {
        Resend resend = new Resend(resendApiKey);

        String verificationUrl = "https://jointless-back-production.up.railway.app/api/v1/users/verify?token=" + token;

        CreateEmailOptions params = CreateEmailOptions.builder()
                .from("onboarding@resend.dev")
                .to(to)
                .subject("Verifica tu cuenta")
                .html(
                        "<p>Pulsa el siguiente enlace para verificar tu cuenta:</p>" +
                                "<a href='" + verificationUrl + "'>Verificar cuenta</a>"
                )
                .build();

        try {
            resend.emails().send(params);
        } catch (ResendException e) {
            throw new RuntimeException("Error enviando email de verificación: " + e.getMessage());
        }
    }
}