package com.example.demo.ui.dtos.user;

import com.example.demo.domain.entities.User;

public record LoginResponse(
        String message,
        User user
) {
}
