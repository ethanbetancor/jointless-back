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
        if (passwordEncoder.matches(password, user.getPassword()) && userRepository.findByEmail(email).isPresent())
            return true;
        return false;
    }

    public void register(String credentialsEncripted) {
        String credentials = cryptographyService.decrypt(credentialsEncripted);
        String[] parts = credentials.split(":");
        String email = parts[0];
        String user = parts[1];
        String password = parts[2];
        userRepository.findByEmail(email).orElseThrow(() -> new EntityExistsException("Ya existe un usuario con este mail"));
        userRepository.save(new User(0, email, user, passwordEncoder.encode(password)));

    }

    public boolean logOut(String credentialsEncripted) {//estoy hay que preguntar
        String credentials = cryptographyService.decrypt(credentialsEncripted);
        String[] parts = credentials.split(":");
        String email = parts[0];
        User user = userRepository.findByEmail(email).orElseThrow(() -> new EntityNotFoundException("No existe ningun usuario con este mail"));
        return true;
    }

    public User getUser(String credentialsEncripted) {
        String credentials = cryptographyService.decrypt(credentialsEncripted);
        String[] parts = credentials.split(":");
        String email = parts[0];
        User user = userRepository.findByEmail(email).orElseThrow(() -> new EntityNotFoundException("No existe ningun usuario con este mail"));
        return user;
    }
}
