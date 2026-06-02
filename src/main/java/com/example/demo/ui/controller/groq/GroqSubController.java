package com.example.demo.ui.controller.groq;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import com.example.demo.domain.service.GroqService;
import com.example.demo.ui.dtos.groq.ApiRequest;
import com.example.demo.ui.dtos.groq.ApiResponse;
import com.example.demo.ui.dtos.groq.GroqResponse;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

@Component
class GroqSubController {
    
    private final GroqService groqService;

    public GroqSubController(GroqService groqService) {
        this.groqService = groqService;
    }

    ResponseEntity<ApiResponse> answer(@RequestBody ApiRequest apiRequest) {
    	
    		if (apiRequest.userPrompt() == null || apiRequest.userPrompt().isBlank() || apiRequest == null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    		
        GroqResponse response = groqService.askGroq(apiRequest.userPrompt());
            
        if (response == null || response.choices().isEmpty()) return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
            
        return ResponseEntity.ok().body(new ApiResponse(response.choices().get(0).message().content()));          
    }
}