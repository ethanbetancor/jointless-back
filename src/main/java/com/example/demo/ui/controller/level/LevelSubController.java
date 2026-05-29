package com.example.demo.ui.controller.level;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.demo.data.Category;
import com.example.demo.domain.entities.Level;
import com.example.demo.domain.security.CredentialsValidator;
import com.example.demo.domain.service.LevelService;
import com.example.demo.domain.service.SolutionService;
import com.example.demo.ui.dtos.lvl.AllLevelRequest;
import com.example.demo.ui.dtos.lvl.LevelCategoryRequest;
import com.example.demo.ui.dtos.lvl.LevelListResponse;
import com.example.demo.ui.dtos.lvl.LevelRequest;
import com.example.demo.ui.dtos.lvl.LevelResponse;

import jakarta.persistence.EntityNotFoundException;

@Component
public class LevelSubController {

	private final CredentialsValidator credentialsValidator;
	private final LevelService levelService;
	private final SolutionService solutionService;

	public LevelSubController(CredentialsValidator credentialsValidator, LevelService levelService,
			SolutionService solutionService) {
		super();
		this.credentialsValidator = credentialsValidator;
		this.levelService = levelService;
		this.solutionService = solutionService;
	}

	public ResponseEntity<LevelResponse> getLevelById(@RequestBody LevelRequest request) {
		if (request.credentialEncripted() == null || request.credentialEncripted().isEmpty()) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		return credentialsValidator.check(request.credentialEncripted()).map(user -> {
			try {
				Level level = levelService.getLevelById(request.id());
				boolean isPassed = solutionService.isLevelPassedByUser(request.id(), user.getId());
				return ResponseEntity.ok(new LevelResponse(level, isPassed));
			} catch (EntityNotFoundException e) {
				ResponseEntity<LevelResponse> respuesta404 = ResponseEntity.status(HttpStatus.NOT_FOUND).build();
				return respuesta404;
			}
		}).orElseGet(() -> ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
	}

	public ResponseEntity<LevelListResponse> getAllLevels(@RequestBody AllLevelRequest request) {
		if (request.credentialEncripted() == null || request.credentialEncripted().isEmpty()) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		return credentialsValidator.check(request.credentialEncripted()).map(user -> {
			List<Level> allLevels = levelService.getAllLevels();
			List<LevelResponse> responseList = allLevels.stream().map(level -> {
				boolean isPassed = solutionService.isLevelPassedByUser(level.getId(), user.getId());
				return new LevelResponse(level, isPassed);
			}).toList();
			return ResponseEntity.ok(new LevelListResponse(responseList));
		}).orElseGet(() -> ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
	}

		public ResponseEntity<LevelListResponse> getLevelsByCategory(LevelCategoryRequest request) {
			if (request.credentialEncripted() == null || request.credentialEncripted().isEmpty()) {
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
			}
			if (request.category() == null || request.category().isEmpty()) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
			}
			return credentialsValidator.check(request.credentialEncripted()).map(user -> {
				Category category = Category.valueOf(request.category().toUpperCase().trim());
				List<Level> listCategory = levelService.getLevelsByCategory(category);
				List<LevelResponse> responseList = listCategory.stream().map(level -> {
					boolean isPassed = solutionService.isLevelPassedByUser(level.getId(), user.getId());
					return new LevelResponse(level, isPassed);
				}).toList();
				return ResponseEntity.ok(new LevelListResponse(responseList));
			}).orElseGet(() -> ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
		}
		
}
