package com.example.demo.ui.dtos.solution;

public record SubmitRequest(
        String code,
        Long LevelId,
        String credentialsEncrypted
) {
}
