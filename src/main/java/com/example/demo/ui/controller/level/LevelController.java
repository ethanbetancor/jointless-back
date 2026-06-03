package com.example.demo.ui.controller.level;

import com.example.demo.ui.dtos.lvl.LevelCategoryRequest;
import com.example.demo.ui.dtos.lvl.LevelListResponse;
import com.example.demo.ui.dtos.lvl.LevelRequest;
import com.example.demo.ui.dtos.lvl.LevelResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/lvl")
public class LevelController {
	private final LevelSubController levelSubcontroller;
	
    public LevelController(LevelSubController levelSubcontroller) {
		this.levelSubcontroller = levelSubcontroller;
	}

	@PostMapping("/get")
    public ResponseEntity<LevelResponse> getLevelById(@RequestBody @Valid LevelRequest request,@Valid Authentication authentication) {
        return levelSubcontroller.getLevelById(request, authentication);
    }

    @PostMapping("/get/all")
    public ResponseEntity<LevelListResponse> getAllLevels(@Valid Authentication authentication) {
        return levelSubcontroller.getAllLevels(authentication);
    }
    
    @PostMapping("/get/category")
    public ResponseEntity<LevelListResponse> getLevelsByCategory(@RequestBody @Valid LevelCategoryRequest request,@Valid Authentication authentication){
    		return levelSubcontroller.getLevelsByCategory(request, authentication);
    }
    
}
