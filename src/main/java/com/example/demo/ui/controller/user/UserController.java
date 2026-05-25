package com.example.demo.ui.controller.user;

import com.example.demo.ui.dtos.user.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
public class UserController {
    private final UserSubcontroller userSubcontroller;

    UserController(UserSubcontroller userSubcontroller) {
        this.userSubcontroller = userSubcontroller;
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(@RequestBody RegisterRequest userData) {
        return userSubcontroller.register(userData.credentialEncripted());
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest userPasswordAndEmail) {
        return userSubcontroller.login(userPasswordAndEmail.credentialEncripted());
    }

    @PostMapping("/logout")
    public ResponseEntity<LogoutResponse> logout() {
        return ResponseEntity.ok(new LogoutResponse("logout exitoso"));
    }

    @PostMapping("/change-password")
    public ResponseEntity<ChangePasswordResponse> changePassword(@RequestBody ChangePasswordRequest request) {
        return userSubcontroller.changePassword(request);
    }
}
