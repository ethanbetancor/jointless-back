package com.example.demo.ui.controller.user;

import com.example.demo.domain.service.UserService;
import com.example.demo.ui.dtos.user.*;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
class UserSubcontroller {
    private final UserService userService;

    public UserSubcontroller(UserService userService) {
        this.userService = userService;
    }

    protected ResponseEntity<LoginResponse> login(LoginRequest request) {
        try {
            LoginResponse response = userService.logIn(request);
            return ResponseEntity.ok(response);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    protected ResponseEntity<RegisterResponse> register(RegisterRequest request) {
        RegisterResponse response = userService.register(request);
        if (response.token() == null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        return ResponseEntity.ok(response);
    }

    protected ResponseEntity<ChangePasswordResponse> changePassword(ChangePasswordRequest request) {
        if (userService.changePassword(request)) {
            return ResponseEntity.ok(new ChangePasswordResponse("Cambio de la password exitoso"));
        }
        return ResponseEntity.badRequest().body(new ChangePasswordResponse("No se pudo realizar el cambio"));
    }
}
