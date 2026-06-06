package com.example.demo.ui.dtos.lvl;

import jakarta.validation.constraints.NotBlank;

public record LevelCategoryRequest(
        @NotBlank(message = "La categoría no puede estar vacía")
        String category) {
}
