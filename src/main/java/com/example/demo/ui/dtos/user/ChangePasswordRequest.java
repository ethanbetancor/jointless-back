package com.example.demo.ui.dtos.user;

public record ChangePasswordRequest(
        boolean isAutenticated,
        String newPassword
) {
}
