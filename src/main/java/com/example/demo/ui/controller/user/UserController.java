package com.example.demo.ui.controller.user;

import com.example.demo.ui.dtos.user.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
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

    @Operation(security = @SecurityRequirement(name = "bearerAuth"))
    @PostMapping("/logout")
    public ResponseEntity<LogoutResponse> logout() {
        return ResponseEntity.ok(new LogoutResponse("logout exitoso"));
    }

    @Operation(security = @SecurityRequirement(name = "bearerAuth"))
    @PostMapping("/change-password")
    public ResponseEntity<ChangePasswordResponse> changePassword(@RequestBody @Valid ChangePasswordRequest request, Authentication authentication) {
        return userSubcontroller.changePassword(request, authentication);
    }

    @GetMapping("/verify")
    public ResponseEntity<VerifyResponse> verify(@RequestParam String token){
        return userSubcontroller.verify(new VerifyRequest(token));
    }
}
