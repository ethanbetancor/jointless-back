package com.example.demo.ui.dtos.solution;

public record SolutionResponse(
        long id,
        long levelId,
        long userId,
        String code,
        boolean passed
) {
}

