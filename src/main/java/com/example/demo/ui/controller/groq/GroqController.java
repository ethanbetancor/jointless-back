package com.example.demo.ui.controller.groq;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.ui.dtos.groq.ApiRequest;
import com.example.demo.ui.dtos.groq.ApiResponse;

@RestController
@RequestMapping("/api/v1/ai")
public class GroqController {

    private final GroqSubController groqSubController;

    public GroqController(GroqSubController groqSubController) {
        this.groqSubController = groqSubController;
    }

    @PostMapping("/clue")
    public ResponseEntity<ApiResponse> clue(@RequestBody ApiRequest apiRequest) {
    	return groqSubController.answer(apiRequest);
    }
}