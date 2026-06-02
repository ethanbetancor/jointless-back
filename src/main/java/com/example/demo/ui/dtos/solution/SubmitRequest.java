package com.example.demo.ui.dtos.solution;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record SubmitRequest(

        @NotBlank(message = "El código no puede estar vacío")
        String code,
        @NotNull(message = "El levelId no puede estar vacío")
        Long levelId
) {
}
