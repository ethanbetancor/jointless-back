package com.example.demo.ui.controller.groq;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.ui.dtos.groq.ApiResponse;

@RestController
@RequestMapping("/api")
public class GroqController {

    private final GroqSubController groqSubController;

    public GroqController(GroqSubController groqSubController) {
        this.groqSubController = groqSubController;
    }

    @PostMapping("/clue")
    public ResponseEntity<ApiResponse> clue(@RequestBody String userPrompt) {
    	return groqSubController.answer(userPrompt);
    }
}