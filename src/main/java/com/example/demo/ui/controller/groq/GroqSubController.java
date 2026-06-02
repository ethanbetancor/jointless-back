package com.example.demo.ui.controller.groq;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import com.example.demo.domain.service.GroqService;
import com.example.demo.ui.dtos.groq.ApiRequest;
import com.example.demo.ui.dtos.groq.ApiResponse;
import com.example.demo.ui.dtos.groq.GroqResponse;

import org.springframework.web.bind.annotation.RequestBody;
@Component
class GroqSubController {
    
    private final GroqService groqService;

    public GroqSubController(GroqService groqService) {
        this.groqService = groqService;
    }

    ResponseEntity<ApiResponse> answer(@RequestBody ApiRequest apiRequest) {
    	
    		if (apiRequest == null || apiRequest.userPrompt() == null || apiRequest.userPrompt().isBlank()) return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    		
        GroqResponse response = groqService.askGroq(apiRequest.userPrompt());
            
        if (response == null || response.choices().isEmpty()) return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
            
        return ResponseEntity.ok().body(new ApiResponse(response.choices().get(0).message().content()));          
    }
}