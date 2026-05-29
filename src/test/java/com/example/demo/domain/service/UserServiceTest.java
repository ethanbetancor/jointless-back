package com.example.demo.domain.service;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
import com.example.demo.ui.dtos.user.ChangePasswordRequest;

import jakarta.persistence.EntityNotFoundException;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
	@Mock
    private CryptographyService cryptographyService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private CredentialsValidator credentialsValidator;

    @InjectMocks
    private UserService userService;
	
    @Test
    void shouldReturnTrueWhenLoginIsValid() {

        when(credentialsValidator.check(anyString()))
                .thenReturn(Optional.of(new User()));

        boolean result = userService.logIn("encrypted");

        assertTrue(result);
    }
    
    @Test
    void shouldReturnFalseWhenLoginIsInvalid() {

        when(credentialsValidator.check(anyString()))
                .thenReturn(Optional.empty());

        boolean result = userService.logIn("encrypted");

        assertFalse(result);
    }
      
    @Test
    void shouldRegisterUserSuccessfully() {

        when(cryptographyService.decrypt(anyString()))
                .thenReturn("email@test.com:user:pass");

        when(userRepository.findByEmail("email@test.com"))
                .thenReturn(Optional.empty());

        when(passwordEncoder.encode("pass"))
                .thenReturn("encodedPass");

        boolean result = userService.register("encrypted");

        assertTrue(result);

        verify(userRepository, times(1))
                .save(any(User.class));
    }
    
    @Test
    void shouldFailRegisterWhenUserAlreadyExists() {

        when(cryptographyService.decrypt(anyString()))
                .thenReturn("email@test.com:user:pass");

        when(userRepository.findByEmail("email@test.com"))
                .thenReturn(Optional.of(new User()));

        boolean result = userService.register("encrypted");

        assertFalse(result);

        verify(userRepository, times(0))
                .save(any());
    }
    
    @Test
    void shouldLogoutSuccessfully() {

        when(cryptographyService.decrypt(anyString()))
                .thenReturn("email@test.com:user:pass");

        when(userRepository.findByEmail("email@test.com"))
                .thenReturn(Optional.of(new User()));

        boolean result = userService.logOut("encrypted");

        assertTrue(result);
    }
    
    @Test
    void shouldThrowWhenLogoutUserNotFound() {

        when(cryptographyService.decrypt(anyString()))
                .thenReturn("email@test.com:user:pass");

        when(userRepository.findByEmail("email@test.com"))
                .thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class,
                () -> userService.logOut("encrypted"));
    }
    
    @Test
    void shouldReturnUserWhenExists() {

        User user = new User();

        when(cryptographyService.decrypt(anyString()))
                .thenReturn("email@test.com:user:pass");

        when(userRepository.findByEmail("email@test.com"))
                .thenReturn(Optional.of(user));

        User result = userService.getUser("encrypted");

        assertNotNull(result);
    }
    
    @Test
    void shouldThrowWhenGetUserNotFound() {

        when(cryptographyService.decrypt(anyString()))
                .thenReturn("email@test.com:user:pass");

        when(userRepository.findByEmail("email@test.com"))
                .thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class,
                () -> userService.getUser("encrypted"));
    }
    
    @Test
    void shouldChangePasswordSuccessfully() {

        User user = new User();
        user.setPassword("oldEncoded");

        when(cryptographyService.decrypt(anyString()))
                .thenReturn("email@test.com:user:old");

        when(userRepository.findByEmail(anyString()))
                .thenReturn(Optional.of(user));

        when(passwordEncoder.matches(anyString(), anyString()))
                .thenReturn(false);

        when(passwordEncoder.encode(anyString()))
                .thenReturn("newEncoded");

        boolean result = userService.changePassword(
                new ChangePasswordRequest("encrypted", "newPass")
        );

        assertTrue(result);

        verify(userRepository).save(any(User.class));
    }
    
    @Test
    void shouldNotChangePasswordIfSamePassword() {

        User user = new User();
        user.setPassword("encoded");

        when(cryptographyService.decrypt(anyString()))
                .thenReturn("email@test.com:user:old");

        when(userRepository.findByEmail(anyString()))
                .thenReturn(Optional.of(user));

        when(passwordEncoder.matches(anyString(), anyString()))
                .thenReturn(true);

        boolean result = userService.changePassword(
                new ChangePasswordRequest("encrypted", "old")
        );

        assertFalse(result);

        verify(userRepository, times(0))
                .save(any());
    }
}
