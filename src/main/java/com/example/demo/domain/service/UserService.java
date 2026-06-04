package com.example.demo.domain.service;

import com.example.demo.data.UserRepository;
import com.example.demo.domain.entities.User;
import com.example.demo.domain.security.CredentialsValidator;
import com.example.demo.ui.dtos.user.*;

import jakarta.persistence.EntityNotFoundException;

import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final CryptographyService cryptographyService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final CredentialsValidator credentialsValidator;
    private final JwtService jwtService;
    private final EmailService emailService;

    public UserService(CryptographyService cryptographyService, UserRepository userRepository, PasswordEncoder passwordEncoder, CredentialsValidator credentialsValidator, JwtService jwtService, EmailService emailService) {
        this.cryptographyService = cryptographyService;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.credentialsValidator = credentialsValidator;
        this.jwtService = jwtService;
        this.emailService = emailService;
    }

    public LoginResponse logIn(LoginRequest request) {
        User user = credentialsValidator.check(request.email(), request.encryptedPassword()).orElseThrow(() -> new EntityNotFoundException("No existe ningun usuario con este mail o contraseña"));
        if (!user.isActive()) throw new IllegalStateException("El usuario no está verificado");
        String token = jwtService.generateToken(user);
        return new LoginResponse(token, user.getName());
    }

    public RegisterResponse register(RegisterRequest request) {
        String password = cryptographyService.decrypt(request.encryptedPassword());

        if (userRepository.findByEmail(request.email()).isPresent()) return new RegisterResponse(null);
        User user = new User(0, request.email(), request.username(), passwordEncoder.encode(password));
        userRepository.save(user);
        String token = jwtService.generateToken(user);
        return new RegisterResponse(token);
    }

    public boolean logOut(String credentialsEncripted) {
        String credentials = cryptographyService.decrypt(credentialsEncripted);
        String[] parts = credentials.split(":");
        String email = parts[0];
        userRepository.findByEmail(email).orElseThrow(() -> new EntityNotFoundException("No existe ningun usuario con este mail"));
        return true;
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }

    public boolean changePassword(ChangePasswordRequest request, Authentication authentication) {
        User user = this.getUserByEmail(authentication.getName());
        String password = cryptographyService.decrypt(request.newPassword());
        if (user == null) return false;
        if (passwordEncoder.matches(password, user.getPassword())) return false;
        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);
        return true;
    }

    public void sendEmail(String email) {
        User user = this.getUserByEmail(email);
        if (user == null) throw new EntityNotFoundException("No existe ningún usuario con este email");
        String token = jwtService.generateEmailToken(user);
        emailService.sendVerificationEmail(email, token);
    }

    public boolean verify(String token) {

        User user = this.getUserByEmail(jwtService.getUsername(token));
        if (user == null) return false;
        if (jwtService.validateToken(token, user) && jwtService.getType(token).equals("EMAIL_VERIFICATION")) {
            user.setActive(true);
            userRepository.save(user);
            return true;
        }
        return false;

    }


}
