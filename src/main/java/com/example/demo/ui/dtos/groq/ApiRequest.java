package com.example.demo.ui.dtos.groq;

import jakarta.validation.constraints.NotBlank;

public record ApiRequest(
		@NotBlank(message = "El prompt no puede estar vacío")
		String userPrompt
) {

}
