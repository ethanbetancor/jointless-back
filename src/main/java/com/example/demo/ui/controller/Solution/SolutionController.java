package com.example.demo.ui.controller.Solution;

import com.example.demo.ui.dtos.solution.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
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
    @Operation(security = @SecurityRequirement(name = "bearerAuth"))
    @PostMapping("/submit")
    public ResponseEntity<SubmitResponse> submit(@RequestBody @Valid SubmitRequest request, @Valid Authentication authentication) {
        return solutionSubController.submit(request, authentication);
    }

    @Operation(security = @SecurityRequirement(name = "bearerAuth"))
    @PostMapping("/level")
    public ResponseEntity<List<SolutionResponse>> getByLevel(@RequestBody @Valid long levelId, @Valid Authentication authentication) {
        return ResponseEntity.ok(List.of());
    }

    @Operation(security = @SecurityRequirement(name = "bearerAuth"))
    @PostMapping("/user")
    public ResponseEntity<SolutionListResponse> getByUser(@Valid Authentication authentication) {
        return solutionSubController.getByUser(authentication);
    }

}