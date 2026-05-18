package com.example.demo.ui.dtos.lvl;

import com.example.demo.domain.entities.Level;

import java.util.List;

public record GetAllLevelsResponse(
        List<Level> levels
) {
}
