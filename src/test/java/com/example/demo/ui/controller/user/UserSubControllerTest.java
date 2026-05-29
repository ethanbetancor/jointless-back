package com.example.demo.ui.controller.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.demo.domain.entities.User;
import com.example.demo.domain.service.UserService;
import com.example.demo.ui.dtos.user.ChangePasswordRequest;
import com.example.demo.ui.dtos.user.ChangePasswordResponse;
import com.example.demo.ui.dtos.user.LoginResponse;
import com.example.demo.ui.dtos.user.RegisterResponse;

@ExtendWith(MockitoExtension.class)
public class UserSubControllerTest {
	 @Mock
	 private UserService userService;

	 @InjectMocks
	 private UserSubcontroller controller;
	 
	 @Test
	 void shouldReturnBadRequestWhenLoginInputIsNull() {

	     ResponseEntity<LoginResponse> response =
	             controller.login(null);

	     assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
	 }
	 
	 @Test
	 void shouldReturnNotFoundWhenLoginFails() {

	     when(userService.logIn(anyString()))
	             .thenReturn(false);

	     ResponseEntity<LoginResponse> response =
	             controller.login("data");

	     assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
	 }
	 
	 @Test
	 void shouldReturnOkWhenLoginIsSuccessful() {

	     User user = new User();
	     user.setUsername("mar");

	     when(userService.logIn(anyString()))
	             .thenReturn(true);

	     when(userService.getUser(anyString()))
	             .thenReturn(user);

	     ResponseEntity<LoginResponse> response =
	             controller.login("data");

	     assertEquals(HttpStatus.OK, response.getStatusCode());
	     assertEquals("mar", response.getBody().username());
	 }
	 
	 @Test
	 void shouldReturnConflictWhenUserAlreadyExists() {

	     when(userService.register(anyString()))
	             .thenReturn(false);

	     ResponseEntity<RegisterResponse> response =
	             controller.register("data");

	     assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
	 }
	 
	 @Test
	 void shouldReturnOkWhenRegisterSuccess() {

	     User user = new User();
	     user.setUsername("mar");

	     when(userService.register(anyString()))
	             .thenReturn(true);

	     when(userService.getUser(anyString()))
	             .thenReturn(user);

	     ResponseEntity<RegisterResponse> response =
	             controller.register("data");

	     assertEquals(HttpStatus.OK, response.getStatusCode());
	     assertEquals("mar", response.getBody().username());
	 }
	 
	 @Test
	 void shouldReturnUnauthorizedWhenNotLoggedIn() {

	     ChangePasswordRequest request =
	             new ChangePasswordRequest("cred", "newPass");

	     when(userService.logIn(anyString()))
	             .thenReturn(false);

	     ResponseEntity<ChangePasswordResponse> response =
	             controller.changePassword(request);

	     assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
	 }
	 
	 @Test
	 void shouldReturnOkWhenPasswordChanged() {

	     ChangePasswordRequest request =
	             new ChangePasswordRequest("cred", "newPass");

	     when(userService.logIn(anyString()))
	             .thenReturn(true);

	     when(userService.changePassword(any()))
	             .thenReturn(true);

	     ResponseEntity<ChangePasswordResponse> response =
	             controller.changePassword(request);

	     assertEquals(HttpStatus.OK, response.getStatusCode());
	 }
	 
	 @Test
	 void shouldReturnBadRequestWhenPasswordChangeFails() {

	     ChangePasswordRequest request =
	             new ChangePasswordRequest("cred", "newPass");

	     when(userService.logIn(anyString()))
	             .thenReturn(true);

	     when(userService.changePassword(any()))
	             .thenReturn(false);

	     ResponseEntity<ChangePasswordResponse> response =
	             controller.changePassword(request);

	     assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
	 }
}
