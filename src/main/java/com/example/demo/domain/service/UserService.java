package com.example.demo.domain.service;

import com.example.demo.data.UserRepository;
import com.example.demo.domain.entities.User;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final CryptographyService cryptographyService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(CryptographyService cryptographyService, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.cryptographyService = cryptographyService;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public boolean logIn(String credentialsEncripted) {
        String credentials = cryptographyService.decrypt(credentialsEncripted);
        String[] parts = credentials.split(":");
        String email = parts[0];
        String password = parts[1];
        User user = userRepository.findByEmail(email).orElseThrow(() -> new EntityNotFoundException("No existe ningun usuario con este mail"));
        return passwordEncoder.matches(password, user.password()) && userRepository.findByEmail(email).isPresent();

    }

    public boolean register(String credentialsEncripted) {
        String credentials = cryptographyService.decrypt(credentialsEncripted);
        String[] parts = credentials.split(":");
        String email = parts[0];
        String user = parts[1];
        String password = parts[2];
        if(userRepository.findByEmail(email).isPresent()) return false;
        userRepository.save(new User(0, email, user, passwordEncoder.encode(password)));
        return true;
    }

    public boolean logOut(String credentialsEncripted) {//estoy hay que preguntar
        String credentials = cryptographyService.decrypt(credentialsEncripted);
        String[] parts = credentials.split(":");
        String email = parts[0];
        userRepository.findByEmail(email).orElseThrow(() -> new EntityNotFoundException("No existe ningun usuario con este mail"));
        return true;
    }

    public User getUser(String credentialsEncripted) {
        String credentials = cryptographyService.decrypt(credentialsEncripted);
        String[] parts = credentials.split(":");
        String email = parts[0];
        return userRepository.findByEmail(email).orElseThrow(() -> new EntityNotFoundException("No existe ningun usuario con este mail"));
        
    }
}
