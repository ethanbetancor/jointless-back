package com.example.demo.domain.security;

import com.example.demo.data.UserRepository;
import com.example.demo.domain.entities.User;
import com.example.demo.domain.service.CryptographyService;
import jakarta.persistence.EntityNotFoundException;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public final class CredentialsValidator {
    private final CryptographyService cryptographyService;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public CredentialsValidator(CryptographyService cryptographyService, PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.cryptographyService = cryptographyService;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    public Optional<User> check(String credentialsEncrypted) {
        String credentials = cryptographyService.decrypt(credentialsEncrypted);
        String[] parts = credentials.split(":");
        String email = parts[0];
        String password = parts[1];
        User user = userRepository.findByEmail(email).orElseThrow(() -> new EntityNotFoundException("No existe ningun usuario con este mail"));
        if (passwordEncoder.matches(password, user.getPassword())) return Optional.of(user);
        else  return Optional.empty();
    }
}
