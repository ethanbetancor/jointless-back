package com.example.demo.ui.dtos.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginRequest(
        @NotBlank(message = "El email no puede estar vacío")
        @Email(message = "El email no es válido")
        String email,
        @NotBlank(message = "La contraseña no puede estar vacía")
        String encryptedPassword
) {}
