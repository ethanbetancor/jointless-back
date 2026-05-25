package com.example.demo.ui.controller;

import com.example.demo.domain.entities.Level;
import com.example.demo.ui.dtos.lvl.LevelResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("api/v1/lvl")
public class LevelController {
	
    @PostMapping("/get")
    public ResponseEntity<LevelResponse> getLevelById(@RequestBody int id) { 
        return ResponseEntity.ok(new LevelResponse(new Level()));
    }

    @GetMapping("/get/all")
    public ResponseEntity<List<LevelResponse>> getAllLevels() {

        return ResponseEntity.ok(List.of());
    }
}
