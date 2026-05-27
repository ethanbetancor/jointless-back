package com.example.demo.ui.controller.level;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.demo.data.SolutionRepository;
import com.example.demo.domain.entities.Level;
import com.example.demo.domain.entities.User;
import com.example.demo.domain.security.CredentialsValidator;
import com.example.demo.domain.service.LevelService;
import com.example.demo.domain.service.SolutionService;
import com.example.demo.ui.dtos.lvl.AllLevelRequest;
import com.example.demo.ui.dtos.lvl.LevelCategoryRequest;
import com.example.demo.ui.dtos.lvl.LevelCategoryResponse;
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

	public ResponseEntity<List<LevelResponse>> getAllLevels(@RequestBody AllLevelRequest request) {
		return credentialsValidator.check(request.credentialEncripted()).map(user -> {
			List<Level> allLevels = levelService.getAllLevels();
			List<LevelResponse> responseList = allLevels.stream().map(level -> {
				boolean isPassed = solutionService.isLevelPassedByUser(level.getId(), user.getId());
				return new LevelResponse(level, isPassed);
			}).toList();
			return ResponseEntity.ok(responseList);
		}).orElseGet(() -> ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
	}

		public ResponseEntity<List<LevelResponse>> getLevelsByCategory(LevelCategoryRequest request) {
			return credentialsValidator.check(request.credentialEncripted()).map(user -> {
				List<Level> listCategory = levelService.getLevelsByCategory(request.category());
				List<LevelResponse> responseList = listCategory.stream().map(level -> {
					boolean isPassed = solutionService.isLevelPassedByUser(level.getId(), user.getId());
					return new LevelResponse(level, isPassed);
				}).toList();
				return ResponseEntity.ok(responseList);
			}).orElseGet(() -> ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
		}
		
	public ResponseEntity<LevelCategoryResponse> isCategoryCompleted(@RequestBody LevelCategoryRequest request) {
		return credentialsValidator.check(request.credentialEncripted()).map(user -> {
			List<Level> levels = levelService.getLevelsByCategory(request.category());
			if (levels.isEmpty()) {
				return ResponseEntity.ok(new LevelCategoryResponse(-1));
			}

			long approvedCount = levels.stream()
					.filter(level -> solutionService.isLevelPassedByUser(level.getId(), user.getId())).count();
			int status;
			if (approvedCount == 0) {
				status = -1;
			} else if (approvedCount == levels.size()) {
				status = 1;
			} else {
				status = 0;
			}
			return ResponseEntity.ok(new LevelCategoryResponse(status));

		}).orElseGet(() -> ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
	}
}
