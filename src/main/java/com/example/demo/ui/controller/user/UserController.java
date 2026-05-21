package com.example.demo.ui.controller.user;

import com.example.demo.ui.dtos.user.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")

public class UserController {
    private final UserSubcontroller userSubcontroller;

    public UserController(UserSubcontroller userSubcontroller) {
        this.userSubcontroller = userSubcontroller;
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(new RegisterResponse("usuario creado correctamente", 1));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        return userSubcontroller.login(request);
    }

    @PostMapping("/logout")
    public ResponseEntity<LogoutResponse> logout() {
        return ResponseEntity.ok(new LogoutResponse("logout exitoso"));
    }

    @PutMapping("/change-password")
    public ResponseEntity<ChangePasswordResponse> changePassword(@RequestBody ChangePasswordRequest request) {
        return ResponseEntity.ok(new ChangePasswordResponse("contraseña cambiada exitosamente"));
    }
}
