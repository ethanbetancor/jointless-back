package com.example.demo.ui.controller.user;

import com.example.demo.ui.dtos.user.*;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/users")
public class UserController {
    private final UserSubcontroller userSubcontroller;

    UserController(UserSubcontroller userSubcontroller) {
        this.userSubcontroller = userSubcontroller;
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(@RequestBody @Valid RegisterRequest userData) {
        return userSubcontroller.register(userData);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody @Valid LoginRequest userPasswordAndEmail) {
        return userSubcontroller.login(userPasswordAndEmail);
    }

    @PostMapping("/logout")
    public ResponseEntity<LogoutResponse> logout() {
        return ResponseEntity.ok(new LogoutResponse("logout exitoso"));
    }

    @PostMapping("/change-password")
    public ResponseEntity<ChangePasswordResponse> changePassword(@RequestBody @Valid ChangePasswordRequest request) {
        return userSubcontroller.changePassword(request);
    }
}
