package com.example.demo.ui.dtos.test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.demo.data.Category;
import com.example.demo.domain.entities.Level;
import com.example.demo.domain.entities.User;
import com.example.demo.domain.security.CredentialsValidator;
import com.example.demo.domain.service.LevelService;
import com.example.demo.domain.service.SolutionService;
import com.example.demo.ui.controller.level.LevelSubController;
import com.example.demo.ui.dtos.lvl.AllLevelRequest;
import com.example.demo.ui.dtos.lvl.LevelCategoryRequest;
import com.example.demo.ui.dtos.lvl.LevelListResponse;
import com.example.demo.ui.dtos.lvl.LevelRequest;
import com.example.demo.ui.dtos.lvl.LevelResponse;

import jakarta.persistence.EntityNotFoundException;


@ExtendWith(MockitoExtension.class)
class LevelSubControllerTest {

    @Mock
    private CredentialsValidator credentialsValidator;

    @Mock
    private LevelService levelService;

    @Mock
    private SolutionService solutionService;

    @InjectMocks
    private LevelSubController levelSubController;

    private final String VALID_CREDENTIALS = "encrypted_token_123";
    private final String INVALID_CREDENTIALS = "invalid_token";
    private final Long USER_ID = 1L;
    private final Long LEVEL_ID = 100L;


    private User mockUser;

    @BeforeEach
    void setUp() {
        mockUser = mock(User.class);
    }

    @Test
    void getLevelById_whenLevelExists() {
        LevelRequest request = mock(LevelRequest.class);
        when(request.credentialEncripted()).thenReturn(VALID_CREDENTIALS);
        when(request.id()).thenReturn(LEVEL_ID);

        when(credentialsValidator.check(VALID_CREDENTIALS)).thenReturn(Optional.of(mockUser));
        when(mockUser.getId()).thenReturn(USER_ID);

        Level mockLevel = mock(Level.class);
        when(levelService.getLevelById(LEVEL_ID)).thenReturn(mockLevel);
        when(solutionService.isLevelPassedByUser(LEVEL_ID, USER_ID)).thenReturn(true);

        ResponseEntity<LevelResponse> response = levelSubController.getLevelById(request);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();

    }

    @Test
    void getLevelById_whenLevelNotFound() {
        LevelRequest request = mock(LevelRequest.class);
        when(request.credentialEncripted()).thenReturn(VALID_CREDENTIALS);
        when(request.id()).thenReturn(LEVEL_ID);

        when(credentialsValidator.check(VALID_CREDENTIALS)).thenReturn(Optional.of(mockUser));
        when(levelService.getLevelById(LEVEL_ID)).thenThrow(new EntityNotFoundException("Level not found"));

        ResponseEntity<LevelResponse> response = levelSubController.getLevelById(request);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isNull();
    }

    @Test
    void getLevelById_whenInvalidCredentials() {
        LevelRequest request = mock(LevelRequest.class);
        when(request.credentialEncripted()).thenReturn(INVALID_CREDENTIALS);
        when(credentialsValidator.check(INVALID_CREDENTIALS)).thenReturn(Optional.empty());

        ResponseEntity<LevelResponse> response = levelSubController.getLevelById(request);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
        assertThat(response.getBody()).isNull();
    }
    
    @Test
    void getLevelById_whenCredentialsAreNullOrEmpty_returns401() {
        LevelRequest request = mock(LevelRequest.class);
        when(request.credentialEncripted()).thenReturn("");
        ResponseEntity<LevelResponse> response = levelSubController.getLevelById(request);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }


    @Test
    void getAllLevels_whenValidCredentials() {
        AllLevelRequest request = mock(AllLevelRequest.class);
        when(request.credentialEncripted()).thenReturn(VALID_CREDENTIALS);

        when(credentialsValidator.check(VALID_CREDENTIALS)).thenReturn(Optional.of(mockUser));
        when(mockUser.getId()).thenReturn(USER_ID);

        Level level1 = mock(Level.class);
        Level level2 = mock(Level.class);
        when(level1.getId()).thenReturn(1L);
        when(level2.getId()).thenReturn(2L);

        when(levelService.getAllLevels()).thenReturn(List.of(level1, level2));
        when(solutionService.isLevelPassedByUser(1L, USER_ID)).thenReturn(true);
        when(solutionService.isLevelPassedByUser(2L, USER_ID)).thenReturn(false);

        ResponseEntity<LevelListResponse> response = levelSubController.getAllLevels(request);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
    }

    @Test
    void getAllLevels_whenInvalidCredentials() {
        AllLevelRequest request = mock(AllLevelRequest.class);
        when(request.credentialEncripted()).thenReturn(INVALID_CREDENTIALS);
        when(credentialsValidator.check(INVALID_CREDENTIALS)).thenReturn(Optional.empty());

        ResponseEntity<LevelListResponse> response = levelSubController.getAllLevels(request);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }
    
    @Test
    void getAllLevels_whenCredentialsAreNullOrEmpty() {
        AllLevelRequest request = mock(AllLevelRequest.class);
        when(request.credentialEncripted()).thenReturn(null);
        ResponseEntity<LevelListResponse> response = levelSubController.getAllLevels(request);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }


    @Test
    void getLevelsByCategory_whenValidCredentials() {
        LevelCategoryRequest request = mock(LevelCategoryRequest.class);
        when(request.credentialEncripted()).thenReturn(VALID_CREDENTIALS);
        when(request.category()).thenReturn("SECUENCIALES");

        when(credentialsValidator.check(VALID_CREDENTIALS)).thenReturn(Optional.of(mockUser));
        when(mockUser.getId()).thenReturn(USER_ID);

        Level levelMock = mock(Level.class);
        when(levelMock.getId()).thenReturn(1L);
        when(levelService.getLevelsByCategory(any(Category.class))).thenReturn(List.of(levelMock));
        when(solutionService.isLevelPassedByUser(1L, USER_ID)).thenReturn(true);

        ResponseEntity<LevelListResponse> response = levelSubController.getLevelsByCategory(request);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
    }

    @Test
    void getLevelsByCategory_whenInvalidCredentials() {
        LevelCategoryRequest request = mock(LevelCategoryRequest.class);
        when(request.credentialEncripted()).thenReturn(INVALID_CREDENTIALS);
        when(request.category()).thenReturn("BAD_CATEGORIA");    
        when(credentialsValidator.check(INVALID_CREDENTIALS)).thenReturn(Optional.empty());
        ResponseEntity<LevelListResponse> response = levelSubController.getLevelsByCategory(request);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }
    
    @Test
    void getLevelsByCategory_whenInvalidCategoryEnum() {
        LevelCategoryRequest request = mock(LevelCategoryRequest.class);
        when(request.credentialEncripted()).thenReturn(VALID_CREDENTIALS);
        when(request.category()).thenReturn("INVALID_CATEGORY");

        when(credentialsValidator.check(VALID_CREDENTIALS)).thenReturn(Optional.of(mockUser));

        org.junit.jupiter.api.Assertions.assertThrows(IllegalArgumentException.class, () -> {
            levelSubController.getLevelsByCategory(request);
        });
    }
    
    @Test
    void getLevelsByCategory_whenCategoryIsNullOrEmpty() {
        LevelCategoryRequest request = mock(LevelCategoryRequest.class);
        when(request.credentialEncripted()).thenReturn(VALID_CREDENTIALS);
        when(request.category()).thenReturn(""); 
        ResponseEntity<LevelListResponse> response = levelSubController.getLevelsByCategory(request);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }
}