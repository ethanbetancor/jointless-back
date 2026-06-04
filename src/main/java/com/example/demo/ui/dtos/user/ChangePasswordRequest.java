package com.example.demo.ui.dtos.user;

import jakarta.validation.constraints.NotBlank;

public record ChangePasswordRequest(
        @NotBlank(message = "La nueva contraseña no puede estar vacía")
        String newPassword
) {
}
