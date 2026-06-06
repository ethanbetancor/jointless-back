package com.example.demo.ui.dtos.test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.demo.domain.entities.Category;
import com.example.demo.data.LevelRepository;
import com.example.demo.domain.entities.Level;
import com.example.demo.domain.service.LevelService;

import jakarta.persistence.EntityNotFoundException;

@ExtendWith(MockitoExtension.class)
class LevelServiceTest {

    @Mock
    private LevelRepository levelRepository;

    @InjectMocks
    private LevelService levelService;

    @Test
    void getLevelById_whenLevelExists() {
        Long idLevel = 1L;
        Level expectedLevel = new Level();
        when(levelRepository.findById(idLevel)).thenReturn(Optional.of(expectedLevel));

        Level result = levelService.getLevelById(idLevel);

        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(expectedLevel);
        verify(levelRepository).findById(idLevel);
    }

    @Test
    void getLevelById_whenLevelDoesNotExist() {
        Long idLevel = 99L;
        when(levelRepository.findById(idLevel)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> levelService.getLevelById(idLevel))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("No existe ningun level con este ID");
        
        verify(levelRepository).findById(idLevel);
    }


    @Test
    void getAllLevels_whenLevelsExist() {
        List<Level> levels = Arrays.asList(new Level(), new Level());
        when(levelRepository.findAll()).thenReturn(levels);

        List<Level> result = levelService.getAllLevels();

        assertThat(result).hasSize(2);
        assertThat(result).isEqualTo(levels);
        verify(levelRepository).findAll();
    }

    @Test
    void getAllLevels_whenNoLevelsExist() {
        when(levelRepository.findAll()).thenReturn(Collections.emptyList());

        List<Level> result = levelService.getAllLevels();

        assertThat(result).isEmpty();
        verify(levelRepository).findAll();
    }

    @Test
    void getLevelsByCategory() {
        Category category = Category.CONDICIONALES; 
        List<Level> expectedLevels = Arrays.asList(new Level());
        when(levelRepository.findByCategory(category)).thenReturn(expectedLevels);

        List<Level> result = levelService.getLevelsByCategory(category);

        assertThat(result).hasSize(1);
        assertThat(result).isEqualTo(expectedLevels);
        verify(levelRepository).findByCategory(category);
    }
}