package com.example.demo.ui.controller.user;


import com.example.demo.domain.service.UserService;
import com.example.demo.ui.dtos.user.LoginRequest;
import com.example.demo.ui.dtos.user.LoginResponse;
import com.example.demo.ui.dtos.user.RegisterRequest;
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


    protected ResponseEntity<LoginResponse> login(LoginRequest request) {
        if(userService.logIn(request.UserPasswordEncripted())){//comentar a izan mejor nombre UserData que le pasas algo mas que solo la password
            return ResponseEntity.ok().body(new LoginResponse("Login exitoso", userService.getUser(request.UserPasswordEncripted()).getUsername()));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
    
    protected ResponseEntity<RegisterResponse> register(RegisterRequest request){
    		if(userService.register(request.userDataEncrypted())) {
    			return ResponseEntity.ok().body(new RegisterResponse("Registro exitoso",userService.getUser(request.userDataEncrypted()).getUsername()));
    		}
    		
    }
}
