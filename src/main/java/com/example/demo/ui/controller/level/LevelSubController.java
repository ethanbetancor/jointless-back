package com.example.demo.ui.controller.level;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.demo.domain.entities.Level;
import com.example.demo.ui.dtos.lvl.LevelRequest;
import com.example.demo.ui.dtos.lvl.LevelResponse;


@Component
public class LevelSubController {
	 
	    public ResponseEntity<LevelResponse> getLevelById(@RequestBody LevelRequest request) { 
	        return ResponseEntity.ok(new LevelResponse(new Level() , true));
	    }
	    
	    
	    public ResponseEntity<List<LevelResponse>> getAllLevels() {
	        return ResponseEntity.ok(List.of());
	    }
}
