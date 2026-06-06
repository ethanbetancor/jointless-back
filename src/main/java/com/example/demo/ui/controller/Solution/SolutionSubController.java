package com.example.demo.ui.controller.Solution;

import com.example.demo.domain.entities.Solution;
import com.example.demo.domain.entities.User;
import com.example.demo.domain.service.SolutionService;
import com.example.demo.domain.service.UserService;
import com.example.demo.ui.dtos.solution.*;

import java.util.List;
import java.util.stream.Collectors;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class SolutionSubController {
    private final SolutionService solutionService;
    private final UserService userService;

    SolutionSubController(SolutionService solutionService, UserService userService) {
        this.solutionService = solutionService;
        this.userService = userService;
    }

    public ResponseEntity<SubmitResponse> submit(@Valid SubmitRequest request, Authentication authentication) {
        User user = userService.getUserByEmail(authentication.getName());
        if (user == null)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        SubmitResponse response = solutionService.submit(request.code(), request.levelId(), user.getId());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    
    public ResponseEntity<SolutionListResponse> getByUser(Authentication authentication) {

        List<Solution> solutions = solutionService.getByUser(authentication.getName());
        SolutionListResponse response = new SolutionListResponse(solutions.stream().map(s->{
            return new SolutionResponse(s.getId(),s.getLevel().getId(),s.getUser().getId(),s.getCode(),s.getImprovementSuggestion(),s.isPassed());
        }).collect(Collectors.toList()));

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }



}
