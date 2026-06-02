package com.example.demo.domain.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class GroqManager {

    private final RestClient client;

    @Value("${groq.api.model}")
    private String model;

    public GroqManager(@Value("${groq.api.url}") String apiUrl, @Value("${groq.api.key}") String apiKey) {
        
        this.client = RestClient.builder()
                .baseUrl(apiUrl)
                .defaultHeader("Authorization", "Bearer " + apiKey)
                .defaultHeader("Content-Type", "application/json")
                .build();
    }

	public RestClient getClient() {
		return client;
	}

	public String getModel() {
		return model;
	}
    
    
}