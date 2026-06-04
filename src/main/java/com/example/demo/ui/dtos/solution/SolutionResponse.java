package com.example.demo.ui.dtos.solution;

public record SolutionResponse(
		Long solutionId,
		Long levelId,
		Long userId,
		String code,
		boolean isPassed
) {
}

