package com.example.demo.ui.controller.Solution;

import com.example.demo.ui.dtos.solution.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/solutions")
public class SolutionController {
    private final SolutionSubController solutionSubController;

    public SolutionController(SolutionSubController solutionSubController) {
        this.solutionSubController = solutionSubController;
    }

    @PostMapping("/submit")
    public ResponseEntity<SubmitResponse> submit(@RequestBody SubmitRequest request) {
        return solutionSubController.submit(request.code(), request.levelId(), request.credentialsEncrypted());
    }

    @PostMapping("/level")
    public ResponseEntity<List<SolutionResponse>> getByLevel(@RequestBody long levelId) {
        return ResponseEntity.ok(List.of());
    }

    @PostMapping("/user")
    public ResponseEntity<SolutionListResponse> getByUser(@RequestBody SolutionRequest request) {
        return solutionSubController.getByUser(request);
    }

}