package com.example.demo.ui.dtos.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record RegisterRequest(
        @NotBlank(message = "El nombre de usuario no puede estar vacío")
        String username,
        @NotBlank(message = "El email no puede estar vacío")
        @Email(message = "El email no es válido")
        String email,
        @NotBlank(message = "La contraseña no puede estar vacía")
        String encryptedPassword
) {}
