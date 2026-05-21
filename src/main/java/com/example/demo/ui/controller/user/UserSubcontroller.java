package com.example.demo.ui.controller.user;


import com.example.demo.domain.service.UserService;
import com.example.demo.ui.dtos.user.LoginResponse;
import com.example.demo.ui.dtos.user.RegisterResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
class UserSubcontroller {
    private final UserService userService;

    public UserSubcontroller(UserService userService) {
        this.userService = userService;
    }


    protected ResponseEntity<LoginResponse> login(String userPasswordAndEmail) {
    		if(userPasswordAndEmail==null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        
    		if(userService.logIn(userPasswordAndEmail)){
            return ResponseEntity.ok().body(new LoginResponse("Login exitoso", userService.getUser(userPasswordAndEmail).getUsername()));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
    
    protected ResponseEntity<RegisterResponse> register(String userData){
		if(userData==null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

    		if(userService.register(userData)) {
    			return ResponseEntity.ok().body(new RegisterResponse("Registro exitoso",userService.getUser(userData).getUsername()));
    		}
    		return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }
}
