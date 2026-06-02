package com.example.demo.domain.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import com.example.demo.ui.dtos.groq.GroqMessage;
import com.example.demo.ui.dtos.groq.GroqRequest;
import com.example.demo.ui.dtos.groq.GroqResponse;

@Service
public class GroqService {
	
	private final RestClient client;
	
	public GroqService() {
		this.client=RestClient.builder()
							  .baseUrl()
							  .defaultHeader(null, null)
							  .defaultHeader(null,null)
							  .build();
	}
	
	public String askGroq(String userPrompt) {
		StringBuilder finalPrompt = new StringBuilder();
		GroqMessage message = new GroqMessage("user", finalPrompt.append(userPrompt).toString());
		GroqRequest request = new GroqRequest(model, List.of(message));
		
		GroqResponse response = client.post()
										.body(request)
										.retrieve()
										.body(GroqResponse.class);
		if(response != null && !response.choices().isEmpty()) return response.choices().get(0).message().content();
		return "No response from Groq services";
	}
}
