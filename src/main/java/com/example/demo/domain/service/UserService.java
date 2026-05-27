package com.example.demo.domain.service;

import com.example.demo.data.UserRepository;
import com.example.demo.domain.entities.User;
import com.example.demo.domain.security.CredentialsValidator;
import com.example.demo.ui.dtos.user.ChangePasswordRequest;

import jakarta.persistence.EntityNotFoundException;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final CryptographyService cryptographyService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final CredentialsValidator credentialsValidator;

    public UserService(CryptographyService cryptographyService, UserRepository userRepository, PasswordEncoder passwordEncoder, CredentialsValidator credentialsValidator) {
        this.cryptographyService = cryptographyService;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.credentialsValidator = credentialsValidator;
    }

    public boolean logIn(String credentialsEncripted) {
        return credentialsValidator.check(credentialsEncripted).isPresent();
    }

    public boolean register(String credentialsEncripted) {
        System.out.println("[" + credentialsEncripted + "]");
        System.out.println(credentialsEncripted.length());
        String credentials = cryptographyService.decrypt(credentialsEncripted);
        String[] parts = credentials.split(":");
        String email = parts[0];
        String user = parts[1];
        String password = parts[2];
        if(userRepository.findByEmail(email).isPresent()) return false;
        userRepository.save(new User(0, email, user, passwordEncoder.encode(password)));
        return true;
    }

    public boolean logOut(String credentialsEncripted) {
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
    
    public boolean changePassword(ChangePasswordRequest request) {
	    	User user = this.getUser(request.credentialEncripted());
	    	String password = cryptographyService.decrypt(request.newPassword());
	    	if (user == null) return false;
	    	if (passwordEncoder.matches(password , user.getPassword())) return false;
	    	user.setPassword(passwordEncoder.encode(password));
	    	userRepository.save(user);
	    	return true;
    }
    
    
}
