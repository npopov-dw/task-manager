package ru.npopov.taskmanager.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Date;

public record TaskRequestDto (
        @Schema(description = "Уникальное наименование задачи", example = "Задача")
        String name,

        @Schema(description = "Описание задачи", example = "Добавить swagger в проект")
        String description,

        @Schema(description = "Запланированная дата выполнения задачи", example = "2025-09-06")
        Date dateExec
) {}
