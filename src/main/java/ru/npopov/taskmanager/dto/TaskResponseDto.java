package ru.npopov.taskmanager.dto;

import lombok.Builder;

import java.time.LocalDate;

@Builder
public record TaskResponseDto (
        Long id,
        String name,
        String description,
        LocalDate dateExec
) {}
