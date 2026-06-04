package com.example.demo.ui.controller.user;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import com.example.demo.domain.service.UserService;
import com.example.demo.ui.dtos.user.*;

@ExtendWith(MockitoExtension.class)
public class UserSubControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserSubcontroller controller;


    @Test
    void login_whenUserNotFound_returns404() {
        LoginRequest request = new LoginRequest("notfound@test.com", "enc");
        when(userService.logIn(request)).thenThrow(new EntityNotFoundException("no user"));

        ResponseEntity<LoginResponse> response = controller.login(request);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void login_whenValidCredentials_returns200WithToken() {
        LoginRequest request = new LoginRequest("user@test.com", "enc");
        when(userService.logIn(request)).thenReturn(new LoginResponse("jwt-token"));

        ResponseEntity<LoginResponse> response = controller.login(request);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().token()).isEqualTo("jwt-token");
    }


    @Test
    void register_whenEmailAlreadyExists_returns409() {
        RegisterRequest request = new RegisterRequest("John", "existing@test.com", "enc");
        when(userService.register(request)).thenReturn(new RegisterResponse(null));

        ResponseEntity<RegisterResponse> response = controller.register(request);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
    }

    @Test
    void register_whenNewUser_returns200WithToken() {
        RegisterRequest request = new RegisterRequest("John", "john@test.com", "enc");
        when(userService.register(request)).thenReturn(new RegisterResponse("jwt-token"));

        ResponseEntity<RegisterResponse> response = controller.register(request);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().token()).isEqualTo("jwt-token");
    }


    @Test
    void changePassword_whenSuccess_returns200WithMessage() {
        ChangePasswordRequest request = new ChangePasswordRequest("newEncPass");
        Authentication auth = mock(Authentication.class);
        when(userService.changePassword(request, auth)).thenReturn(true);

        ResponseEntity<ChangePasswordResponse> response = controller.changePassword(request, auth);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().message()).isEqualTo("Cambio de la password exitoso");
    }

    @Test
    void changePassword_whenFails_returns400WithMessage() {
        ChangePasswordRequest request = new ChangePasswordRequest("newEncPass");
        Authentication auth = mock(Authentication.class);
        when(userService.changePassword(request, auth)).thenReturn(false);

        ResponseEntity<ChangePasswordResponse> response = controller.changePassword(request, auth);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().message()).isEqualTo("No se pudo realizar el cambio");
    }
}
