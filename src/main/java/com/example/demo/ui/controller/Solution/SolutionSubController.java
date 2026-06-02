package com.example.demo.ui.controller.Solution;

import com.example.demo.domain.entities.Solution;
import com.example.demo.domain.entities.User;
import com.example.demo.domain.security.CredentialsValidator;
import com.example.demo.domain.service.SolutionService;
import com.example.demo.ui.dtos.solution.SolutionListResponse;
import com.example.demo.ui.dtos.solution.SolutionRequest;
import com.example.demo.ui.dtos.solution.SolutionResponse;
import com.example.demo.ui.dtos.solution.SubmitResponse;

import java.util.List;

import org.hibernate.query.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

@Component
public class SolutionSubController {
    private final SolutionService solutionService;
    private final CredentialsValidator credentialsValidator;


    SolutionSubController(SolutionService solutionService, CredentialsValidator credentialsValidator) {
        this.solutionService = solutionService;
        this.credentialsValidator = credentialsValidator;
    }

    public ResponseEntity<SubmitResponse> submit(String code, Long levelId, String credentialsEncrypted){
        if(credentialsEncrypted == null ||levelId == null ||code == null || code.isBlank() || credentialsEncrypted.isBlank()) return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        User user = credentialsValidator.check(credentialsEncrypted).orElse(null);
        if(user == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        SubmitResponse response = solutionService.submit(code, levelId, user.getId());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    
    public ResponseEntity<SolutionListResponse> getByUser(SolutionRequest request) {
        if (request.credentialsEncrypted() == null || request.credentialsEncrypted().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return credentialsValidator.check(request.credentialsEncrypted())
                .map(user -> {
                    List<Solution> listSolution = solutionService.getByUser(user.getId());
                    SolutionListResponse responseBody = new SolutionListResponse(
                        listSolution.stream()
                            .map(solution -> new SolutionResponse(
                                solution.getId(),
                                solution.getLevel().getId(),
                                solution.getUser().getId(),
                                solution.getCode(),
                                solution.isPassed()
                            ))
                            .toList()
                    );
                    return ResponseEntity.ok(responseBody);
                })
                .orElseGet(() -> ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
    }

}
