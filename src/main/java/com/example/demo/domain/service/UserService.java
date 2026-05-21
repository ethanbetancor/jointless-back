package com.example.demo.domain.service;

import com.example.demo.data.UserRepository;
import com.example.demo.domain.entities.User;
import com.example.demo.domain.exceptions.NoSuchUserException;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final CryptographyService cryptographyService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public boolean logIn(String credentialsEncripted){
        String credentials = cryptographyService.decrypt(credentialsEncripted);
        String[] parts = credentials.split(":");
        String email = parts[0];
        String password = parts[1];
        User user = userRepository.findByEmail(email).orElseThrow(() -> //new EntityNotFoundException("No existe ningun usuario con este mail"));
        if(passwordEncoder.matches(password, user.getPassword()) /*&& userRepository.findByEmail(email).isPresent()) {
	        	if(!user.isAuthenticated()) user.setAuthenticated(true);
	    		return user.isAuthenticated();*/
        		return true;
        }
         return false;
    }
    
    public void register(String credentialsEncripted) {
    	    String credentials = cryptographyService.decrypt(credentialsEncripted);
        String[] parts = credentials.split(":");
        String email = parts[0];
        String password = parts[1];
        userRepository.findByEmail(email).orElseThrow(() -> new EntityExistsException());      
        userRepository.save(new User(email,passwordEncoder.encode(password),true));
        
    }
    
    public boolean logOut(String credentialsEncripted) {
    	 	String credentials = cryptographyService.decrypt(credentialsEncripted);
        String[] parts = credentials.split(":");
        String email = parts[0];
        User user = userRepository.findByEmail(email).orElseThrow(() -> new EntityNotFoundException("No existe ningun usuario con este mail"));
        if(user.isAutenthicated()) {
        		user.setAuthenticated(false);
        		return true;
        }
        return user.isAuthenticated();
        
        
    }
}
