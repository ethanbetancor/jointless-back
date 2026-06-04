package com.example.demo.domain.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.demo.data.UserRepository;
import com.example.demo.domain.entities.User;
import com.example.demo.domain.security.CredentialsValidator;
import com.example.demo.ui.dtos.user.*;

import org.springframework.security.core.Authentication;

import jakarta.persistence.EntityNotFoundException;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock private CryptographyService cryptographyService;
    @Mock private UserRepository userRepository;
    @Mock private PasswordEncoder passwordEncoder;
    @Mock private CredentialsValidator credentialsValidator;
    @Mock private JwtService<Object> jwtService;

    @InjectMocks
    private UserService userService;


    @Test
    void shouldReturnLoginResponseWhenLoginIsValid() {
        User user = new User();
        LoginRequest request = new LoginRequest("user@test.com", "encPass");

        when(credentialsValidator.check(anyString(), anyString())).thenReturn(Optional.of(user));
        when(jwtService.generateToken(user)).thenReturn("jwt-token");

        LoginResponse result = userService.logIn(request);

        assertNotNull(result);
        assertEquals("jwt-token", result.token());
        assertEquals(user.getName(), result.username());
    }

    @Test
    void shouldThrowWhenLoginIsInvalid() {
        LoginRequest request = new LoginRequest("user@test.com", "encPass");

        when(credentialsValidator.check(anyString(), anyString())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> userService.logIn(request));
    }

    @Test
    void shouldThrowWhenUserIsNotActive() {
        User inactiveUser = new User(); // isActive es false por defecto
        LoginRequest request = new LoginRequest("user@test.com", "encPass");

        when(credentialsValidator.check(anyString(), anyString())).thenReturn(Optional.of(inactiveUser));

        assertThrows(IllegalStateException.class, () -> userService.logIn(request));
    }


    @Test
    void shouldRegisterUserSuccessfully() {
        RegisterRequest request = new RegisterRequest("John", "john@test.com", "encPass");

        when(cryptographyService.decrypt(anyString())).thenReturn("plainPass");
        when(userRepository.findByEmail("john@test.com")).thenReturn(Optional.empty());
        when(passwordEncoder.encode("plainPass")).thenReturn("encodedPass");
        when(jwtService.generateToken(any(User.class))).thenReturn("jwt-token");

        RegisterResponse result = userService.register(request);

        assertNotNull(result.token());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void shouldFailRegisterWhenUserAlreadyExists() {
        RegisterRequest request = new RegisterRequest("John", "existing@test.com", "encPass");

        when(cryptographyService.decrypt(anyString())).thenReturn("plainPass");
        when(userRepository.findByEmail("existing@test.com")).thenReturn(Optional.of(new User()));

        RegisterResponse result = userService.register(request);

        assertNull(result.token());
        verify(userRepository, times(0)).save(any());
    }


    @Test
    void shouldLogoutSuccessfully() {
        when(cryptographyService.decrypt(anyString())).thenReturn("email@test.com:user:pass");
        when(userRepository.findByEmail("email@test.com")).thenReturn(Optional.of(new User()));

        boolean result = userService.logOut("encrypted");

        assertTrue(result);
    }

    @Test
    void shouldThrowWhenLogoutUserNotFound() {
        when(cryptographyService.decrypt(anyString())).thenReturn("email@test.com:user:pass");
        when(userRepository.findByEmail("email@test.com")).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> userService.logOut("encrypted"));
    }


    @Test
    void shouldChangePasswordSuccessfully() {
        User user = new User();
        user.setPassword("oldEncoded");

        Authentication auth = mock(Authentication.class);
        when(auth.getName()).thenReturn("user@test.com");
        when(userRepository.findByEmail("user@test.com")).thenReturn(Optional.of(user));
        when(cryptographyService.decrypt(anyString())).thenReturn("plainNewPassword");
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(false);
        when(passwordEncoder.encode(anyString())).thenReturn("newEncoded");

        boolean result = userService.changePassword(new ChangePasswordRequest("encryptedNewPass"), auth);

        assertTrue(result);
        verify(userRepository).save(any(User.class));
    }

    @Test
    void shouldNotChangePasswordIfSamePassword() {
        User user = new User();
        user.setPassword("encoded");

        Authentication auth = mock(Authentication.class);
        when(auth.getName()).thenReturn("user@test.com");
        when(userRepository.findByEmail("user@test.com")).thenReturn(Optional.of(user));
        when(cryptographyService.decrypt(anyString())).thenReturn("samePassword");
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(true);

        boolean result = userService.changePassword(new ChangePasswordRequest("encryptedSame"), auth);

        assertFalse(result);
        verify(userRepository, times(0)).save(any());
    }

    @Test
    void shouldReturnFalseWhenUserNotFoundOnChangePassword() {
        Authentication auth = mock(Authentication.class);
        when(auth.getName()).thenReturn("notfound@test.com");
        when(userRepository.findByEmail("notfound@test.com")).thenReturn(Optional.empty());

        boolean result = userService.changePassword(new ChangePasswordRequest("encryptedNewPass"), auth);

        assertFalse(result);
        verify(userRepository, times(0)).save(any());
    }
}
