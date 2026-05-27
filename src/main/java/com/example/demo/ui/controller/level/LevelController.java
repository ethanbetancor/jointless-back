package com.example.demo.ui.controller.level;

import com.example.demo.domain.entities.Level;
import com.example.demo.ui.dtos.lvl.AllLevelRequest;
import com.example.demo.ui.dtos.lvl.LevelCategoryRequest;
import com.example.demo.ui.dtos.lvl.LevelCategoryResponse;
import com.example.demo.ui.dtos.lvl.LevelRequest;
import com.example.demo.ui.dtos.lvl.LevelResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("api/v1/lvl")
public class LevelController {
	private final LevelSubController levelSubcontroller;
	
    public LevelController(LevelSubController levelSubcontroller) {
		this.levelSubcontroller = levelSubcontroller;
	}

	@PostMapping("/get")
    public ResponseEntity<LevelResponse> getLevelById(@RequestBody LevelRequest request) { 
        return levelSubcontroller.getLevelById(request);
    }

    @GetMapping("/get/all")
    public ResponseEntity<List<LevelResponse>> getAllLevels(@RequestBody AllLevelRequest request) {
        return levelSubcontroller.getAllLevels(request);
    }
    
    @PostMapping("/get/category")
    public ResponseEntity<List<LevelResponse>> getLevelsByCategory(@RequestBody LevelCategoryRequest request){
    		return levelSubcontroller.getLevelsByCategory(request);
    }
    
    @PostMapping("/get/isCategoryCompleted")
    public ResponseEntity<LevelCategoryResponse> isCategoryCompleted(@RequestBody LevelCategoryRequest request){
    		return levelSubcontroller.isCategoryCompleted(request);
    }
}
