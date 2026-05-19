package com.example.demo.ui.dtos.user;

public record RegisterRequest(
        String username,
        String email,
        String password
) {
}

