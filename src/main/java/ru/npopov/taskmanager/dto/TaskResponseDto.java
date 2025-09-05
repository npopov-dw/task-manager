package ru.npopov.taskmanager.dto;

import java.util.Date;

public record TaskResponseDto (
        Long id,
        String name,
        String description,
        Date dateExec
) {}
