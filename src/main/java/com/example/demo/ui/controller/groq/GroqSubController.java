package com.example.demo.ui.controller.groq;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import com.example.demo.domain.service.GroqService;
import com.example.demo.ui.dtos.groq.ApiRequest;
import com.example.demo.ui.dtos.groq.ApiResponse;
import com.example.demo.ui.dtos.groq.GroqResponse;

@Component
class GroqSubController {
    
    private final GroqService groqService;

    public GroqSubController(GroqService groqService) {
        this.groqService = groqService;
    }

    public ResponseEntity<ApiResponse> answerClue(ApiRequest apiRequest) {   		
        return verifyResponse(groqService.askGroqForClue(apiRequest.userPrompt()));      
    }
    
    public ResponseEntity<ApiResponse> answerImprovement(ApiRequest apiRequest) {
    	return verifyResponse(groqService.askGroqForImprovement(apiRequest.userPrompt()));           
    }
    
    private ResponseEntity<ApiResponse> verifyResponse(GroqResponse response) {
    	if (response == null || response.choices().isEmpty()) return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        
        return ResponseEntity.ok().body(new ApiResponse(response.choices().get(0).message().content())); 
    }
}