package com.example.demo.ui.controller;

import com.example.demo.domain.entities.User;
import com.example.demo.ui.dtos.user.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @GetMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(new LoginResponse("usuario creadio correctamente",new User()));
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
