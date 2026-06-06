package com.example.demo.ui.dtos.test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import com.example.demo.domain.entities.Category;
import com.example.demo.domain.entities.Level;
import com.example.demo.domain.service.LevelService;
import com.example.demo.domain.service.SolutionService;
import com.example.demo.ui.controller.level.LevelSubController;
import com.example.demo.ui.dtos.lvl.LevelCategoryRequest;
import com.example.demo.ui.dtos.lvl.LevelListResponse;
import com.example.demo.ui.dtos.lvl.LevelRequest;
import com.example.demo.ui.dtos.lvl.LevelResponse;

import jakarta.persistence.EntityNotFoundException;

@ExtendWith(MockitoExtension.class)
class LevelSubControllerTest {

    @Mock
    private LevelService levelService;

    @Mock
    private SolutionService solutionService;

    @InjectMocks
    private LevelSubController levelSubController;

    private final Long LEVEL_ID = 100L;


    @Test
    void getLevelById_whenLevelExists() {
        Authentication auth = mock(Authentication.class);
        when(auth.getName()).thenReturn("user@test.com");

        LevelRequest request = new LevelRequest(LEVEL_ID);
        Level mockLevel = mock(Level.class);

        when(levelService.getLevelById(LEVEL_ID)).thenReturn(mockLevel);
        when(solutionService.isLevelPassedByUserEmail(LEVEL_ID, "user@test.com")).thenReturn(true);

        ResponseEntity<LevelResponse> response = levelSubController.getLevelById(request, auth);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
    }

    @Test
    void getLevelById_whenLevelNotFound() {
        Authentication auth = mock(Authentication.class);

        LevelRequest request = new LevelRequest(LEVEL_ID);
        when(levelService.getLevelById(LEVEL_ID)).thenThrow(new EntityNotFoundException("Level not found"));

        ResponseEntity<LevelResponse> response = levelSubController.getLevelById(request, auth);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isNull();
    }


    @Test
    void getAllLevels_whenLevelsExist() {
        Authentication auth = mock(Authentication.class);
        when(auth.getName()).thenReturn("user@test.com");

        Level level1 = mock(Level.class);
        Level level2 = mock(Level.class);
        when(level1.getId()).thenReturn(1L);
        when(level2.getId()).thenReturn(2L);

        when(levelService.getAllLevels()).thenReturn(List.of(level1, level2));
        when(solutionService.isLevelPassedByUserEmail(anyLong(), anyString())).thenReturn(false);

        ResponseEntity<LevelListResponse> response = levelSubController.getAllLevels(auth);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
    }

    @Test
    void getAllLevels_whenNoLevels_returns404() {
        Authentication auth = mock(Authentication.class);
        when(levelService.getAllLevels()).thenReturn(Collections.emptyList());

        ResponseEntity<LevelListResponse> response = levelSubController.getAllLevels(auth);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }


    @Test
    void getLevelsByCategory_whenValidCategory() {
        Authentication auth = mock(Authentication.class);
        when(auth.getName()).thenReturn("user@test.com");

        LevelCategoryRequest request = new LevelCategoryRequest("SECUENCIALES");
        Level levelMock = mock(Level.class);
        when(levelMock.getId()).thenReturn(1L);

        when(levelService.getLevelsByCategory(any(Category.class))).thenReturn(List.of(levelMock));
        when(solutionService.isLevelPassedByUserEmail(anyLong(), anyString())).thenReturn(true);

        ResponseEntity<LevelListResponse> response = levelSubController.getLevelsByCategory(request, auth);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
    }

    @Test
    void getLevelsByCategory_whenInvalidCategoryEnum_throwsException() {
        Authentication auth = mock(Authentication.class);
        LevelCategoryRequest request = new LevelCategoryRequest("INVALID_CATEGORY");

        assertThatThrownBy(() -> levelSubController.getLevelsByCategory(request, auth))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void getLevelsByCategory_whenNoLevels_returns404() {
        Authentication auth = mock(Authentication.class);
        when(levelService.getLevelsByCategory(any(Category.class))).thenReturn(Collections.emptyList());

        LevelCategoryRequest request = new LevelCategoryRequest("SECUENCIALES");

        ResponseEntity<LevelListResponse> response = levelSubController.getLevelsByCategory(request, auth);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }
}