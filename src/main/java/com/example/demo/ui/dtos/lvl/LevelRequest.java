package com.example.demo.ui.dtos.lvl;

import jakarta.validation.constraints.NotNull;

public record LevelRequest(
        @NotNull(message = "El id del nivel no puede estar vacío")
        Long id) {
}
