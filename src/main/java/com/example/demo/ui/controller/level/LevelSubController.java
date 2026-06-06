package com.example.demo.ui.controller.level;

import java.util.List;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.example.demo.domain.entities.Category;
import com.example.demo.domain.entities.Level;
import com.example.demo.domain.service.LevelService;
import com.example.demo.domain.service.SolutionService;
import com.example.demo.ui.dtos.lvl.LevelCategoryRequest;
import com.example.demo.ui.dtos.lvl.LevelListResponse;
import com.example.demo.ui.dtos.lvl.LevelRequest;
import com.example.demo.ui.dtos.lvl.LevelResponse;

import jakarta.persistence.EntityNotFoundException;

@Component
public class LevelSubController {

    private final LevelService levelService;
    private final SolutionService solutionService;

    public LevelSubController(LevelService levelService, SolutionService solutionService) {
        super();
        this.levelService = levelService;
        this.solutionService = solutionService;
    }

    public ResponseEntity<LevelResponse> getLevelById(LevelRequest request, Authentication authentication) {
        try {
            Level level = levelService.getLevelById(request.id());
            boolean isPassed = solutionService.isLevelPassedByUserEmail(request.id(), authentication.getName());
            return ResponseEntity.ok(new LevelResponse(level, isPassed));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

    }

    public ResponseEntity<LevelListResponse> getAllLevels(Authentication authentication) {
        List<Level> allLevels = levelService.getAllLevels();
        return getLevelListResponseResponseEntity(authentication, allLevels);
    }

    public ResponseEntity<LevelListResponse> getLevelsByCategory(LevelCategoryRequest request, Authentication authentication) {

        Category category = Category.valueOf(request.category().toUpperCase().trim());
        List<Level> listCategory = levelService.getLevelsByCategory(category);
        return getLevelListResponseResponseEntity(authentication, listCategory);
    }

    private ResponseEntity<LevelListResponse> getLevelListResponseResponseEntity(Authentication authentication, List<Level> listCategory) {
        List<LevelResponse> responseList = listCategory.stream().map(level -> {
            boolean isPassed = solutionService.isLevelPassedByUserEmail(level.getId(), authentication.getName());
            return new LevelResponse(level, isPassed);
        }).toList();
        if (responseList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(new LevelListResponse(responseList));
    }


}
