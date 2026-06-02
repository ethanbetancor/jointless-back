package com.example.demo.ui.dtos.groq;

import java.util.List;

public record GroqResponse(
		List<Choice> choices
) {
	
	public record Choice(
			GroqMessage message
	) {}
	
}
