package com.example.demo.ui.controller.groq;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.ui.dtos.groq.ApiRequest;
import com.example.demo.ui.dtos.groq.ApiResponse;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/ai")
public class GroqController {

    private final GroqSubController groqSubController;

    public GroqController(GroqSubController groqSubController) {
        this.groqSubController = groqSubController;
    }

    @PostMapping("/clue")
    public ResponseEntity<ApiResponse> clue(@Valid @RequestBody ApiRequest apiRequest) {
    	return groqSubController.answerClue(apiRequest);
    }
}