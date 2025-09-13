package ru.npopov.taskmanager.util;

import ru.npopov.taskmanager.dto.TaskRequestDto;
import ru.npopov.taskmanager.dto.TaskResponseDto;
import ru.npopov.taskmanager.persistence.entity.Task;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public class TaskTestData {

    public static final String TASK_URL_TEMPLATE = "/api/v1/tasks";
    public static final Long DEFAULT_ID = 3L;
    private static final String DEFAULT_TASK_NAME = "Task 1";
    private static final String DEFAULT_TASK_DESCRIPTION = "Description task 1";
    private static final LocalDate DEFAULT_DATE_EXEC = LocalDate.parse("2025-09-05");
    private static final LocalDateTime DEFAULT_CREATED_AT = LocalDateTime.parse("2025-09-01T12:30:00");
    private static final LocalDateTime DEFAULT_UPDATED_AT = LocalDateTime.parse("2025-09-01T12:30:00");
    private static final Long DEFAULT_ID_TWO = 2L;
    private static final String DEFAULT_TASK_NAME_TWO = "Task 2";
    private static final String DEFAULT_TASK_DESCRIPTION_TWO = "Description task 2";
    private static final LocalDate DEFAULT_DATE_EXEC_TWO = LocalDate.parse("2025-09-13");
    private static final LocalDateTime DEFAULT_CREATED_AT_TWO = LocalDateTime.parse("2026-09-12T12:30:00");
    private static final LocalDateTime DEFAULT_UPDATED_AT_TWO = LocalDateTime.parse("2026-09-12T12:30:00");

    public static TaskRequestDto createTaskRequestDto() {
        return TaskRequestDto.builder()
                .name(DEFAULT_TASK_NAME)
                .description(DEFAULT_TASK_DESCRIPTION)
                .dateExec(DEFAULT_DATE_EXEC)
                .build();
    }

    public static TaskRequestDto createTaskRequestDtoToUpdate() {
        return TaskRequestDto.builder()
                .name(DEFAULT_TASK_NAME_TWO)
                .description(DEFAULT_TASK_DESCRIPTION_TWO)
                .dateExec(DEFAULT_DATE_EXEC_TWO)
                .build();
    }


    public static TaskResponseDto createTaskResponseDto() {
        return TaskResponseDto.builder()
                .id(DEFAULT_ID)
                .name(DEFAULT_TASK_NAME)
                .description(DEFAULT_TASK_DESCRIPTION)
                .dateExec(DEFAULT_DATE_EXEC)
                .build();
    }

    public static TaskResponseDto createUpdatedTaskResponseDto() {
        return TaskResponseDto.builder()
                .id(DEFAULT_ID)
                .name(DEFAULT_TASK_NAME_TWO)
                .description(DEFAULT_TASK_DESCRIPTION_TWO)
                .dateExec(DEFAULT_DATE_EXEC_TWO)
                .build();
    }

    public static Task createTaskToSaving() {
        return Task.builder()
                .name(DEFAULT_TASK_NAME)
                .description(DEFAULT_TASK_DESCRIPTION)
                .dateExec(DEFAULT_DATE_EXEC)
                .build();
    }

    public static Task createTask() {
        return Task.builder()
                .name(DEFAULT_TASK_NAME)
                .description(DEFAULT_TASK_DESCRIPTION)
                .dateExec(DEFAULT_DATE_EXEC)
                .createdAt(DEFAULT_CREATED_AT)
                .updatedAt(DEFAULT_UPDATED_AT)
                .build();
    }

    public static Task createUpdatedTask() {
        return Task.builder()
                .name(DEFAULT_TASK_NAME_TWO)
                .description(DEFAULT_TASK_DESCRIPTION_TWO)
                .dateExec(DEFAULT_DATE_EXEC_TWO)
                .createdAt(DEFAULT_CREATED_AT)
                .updatedAt(DEFAULT_UPDATED_AT)
                .build();
    }

    public static Task createTaskToUpdate() {
        return Task.builder()
                .name(DEFAULT_TASK_NAME_TWO)
                .description(DEFAULT_TASK_DESCRIPTION_TWO)
                .dateExec(DEFAULT_DATE_EXEC_TWO)
                .build();
    }

    public static List<Task> createTasks() {
        return Arrays.asList(
                Task.builder()
                    .name(DEFAULT_TASK_NAME)
                    .description(DEFAULT_TASK_DESCRIPTION)
                    .dateExec(DEFAULT_DATE_EXEC)
                    .createdAt(DEFAULT_CREATED_AT)
                    .updatedAt(DEFAULT_UPDATED_AT)
                    .build(),
                Task.builder()
                        .name(DEFAULT_TASK_NAME_TWO)
                        .description(DEFAULT_TASK_DESCRIPTION_TWO)
                        .dateExec(DEFAULT_DATE_EXEC_TWO)
                        .createdAt(DEFAULT_CREATED_AT_TWO)
                        .updatedAt(DEFAULT_UPDATED_AT_TWO)
                        .build()
                );
    }

    public static List<TaskResponseDto> createTaskResponseDtos() {
        return Arrays.asList(
                TaskResponseDto.builder()
                        .id(DEFAULT_ID)
                        .name(DEFAULT_TASK_NAME)
                        .description(DEFAULT_TASK_DESCRIPTION)
                        .dateExec(DEFAULT_DATE_EXEC)
                        .build(),
                TaskResponseDto.builder()
                        .id(DEFAULT_ID_TWO)
                        .name(DEFAULT_TASK_NAME_TWO)
                        .description(DEFAULT_TASK_DESCRIPTION_TWO)
                        .dateExec(DEFAULT_DATE_EXEC_TWO)
                        .build()
        );
    }
}
