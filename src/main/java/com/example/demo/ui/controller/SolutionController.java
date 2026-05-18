package com.example.demo.ui.controller;

import com.example.demo.ui.dtos.solution.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/solutions")
public class SolutionController {

    @PostMapping
    public ResponseEntity<SubmitSolutionResponse> submit(@RequestBody SubmitSolutionRequest request) {
        return ResponseEntity.ok(new SubmitSolutionResponse("solución enviada correctamente", false));
    }

    @GetMapping("/level/{levelId}")
    public ResponseEntity<List<SolutionResponse>> getByLevel(@PathVariable long levelId) {
        return ResponseEntity.ok(List.of());
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<SolutionResponse>> getByUser(@PathVariable long userId) {
        return ResponseEntity.ok(List.of());
    }

}