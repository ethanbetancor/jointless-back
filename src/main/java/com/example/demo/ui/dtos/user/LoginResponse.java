package com.example.demo.ui.dtos.user;

public record LoginResponse(
        String token,
        String username
) {
}
