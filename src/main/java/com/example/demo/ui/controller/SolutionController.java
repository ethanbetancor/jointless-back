package com.example.demo.ui.controller;

import com.example.demo.ui.dtos.solution.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/solutions")
public class SolutionController {

    @PostMapping
    public ResponseEntity<SubmitSolutionResponse> submit(@RequestBody SubmitSolutionRequest request) {
        return ResponseEntity.ok(new SubmitSolutionResponse("solución enviada correctamente", false));
    }

    @PostMapping("/level")
    public ResponseEntity<List<SolutionResponse>> getByLevel(@RequestBody long levelId) {
        return ResponseEntity.ok(List.of());
    }

    @PostMapping("/user")
    public ResponseEntity<List<SolutionResponse>> getByUser(@RequestBody long userId) {
        return ResponseEntity.ok(List.of());
    }

}