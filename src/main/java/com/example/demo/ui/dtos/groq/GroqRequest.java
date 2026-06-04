package com.example.demo.ui.dtos.groq;

import java.util.List;

public record GroqRequest(
		String model, 
		List<GroqMessage> messages
) {

}
