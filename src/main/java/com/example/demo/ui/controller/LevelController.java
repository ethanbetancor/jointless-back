package com.example.demo.ui.controller;

import com.example.demo.domain.entities.Level;
import com.example.demo.ui.dtos.lvl.GetAllLevelsResponse;
import com.example.demo.ui.dtos.lvl.GetLevelResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/lvl")
public class LevelController {
    @GetMapping("/{id}")
    public ResponseEntity<GetLevelResponse> getLevel(@PathVariable long id) {
        return ResponseEntity.ok(new GetLevelResponse(new Level()));
    }

    @GetMapping("/all")
    public ResponseEntity<GetAllLevelsResponse> getAllLevels() {
        return ResponseEntity.ok(new GetAllLevelsResponse(new ArrayList<>()));
    }
}
