package ru.npopov.taskmanager.dto;

import java.util.Date;

public record TaskRequestDto (
        String name,
        String description,
        Date dateExec
) {}
