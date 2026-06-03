package com.example.demo.ui.controller.Solution;

import com.example.demo.ui.dtos.solution.*;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
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
    public ResponseEntity<SubmitResponse> submit(@RequestBody @Valid SubmitRequest request, @Valid Authentication authentication) {
        return solutionSubController.submit(request, authentication);
    }

    @PostMapping("/level")
    public ResponseEntity<List<SolutionResponse>> getByLevel(@RequestBody @Valid long levelId) {
        return ResponseEntity.ok(List.of());
    }

    @PostMapping("/user")
    public ResponseEntity<List<SolutionResponse>> getByUser(@RequestBody long userId) {
        return ResponseEntity.ok(List.of());
    }

}