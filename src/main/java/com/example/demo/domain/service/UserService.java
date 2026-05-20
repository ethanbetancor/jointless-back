package com.example.demo.domain.service;

import com.example.demo.data.UserRepository;
import com.example.demo.domain.entities.User;
import com.example.demo.domain.exceptions.NoSuchUserException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final CryptographyService cryptographyService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public boolean login(String credentialsEncripted){
        String credentials = cryptographyService.decrypt(credentialsEncripted);
        String[] parts = credentials.split(":");
        String email = parts[0];
        String password = parts[1];
        User user = userRepository.findByEmail(email).orElseThrow(() -> new NoSuchUserException("No existe ningun usuario con este mail"));
        if(passwordEncoder.matches(password, user.getPassword())) return true;
        else return false;
    }

}
