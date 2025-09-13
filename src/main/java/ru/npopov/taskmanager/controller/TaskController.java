package ru.npopov.taskmanager.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.npopov.taskmanager.dto.TaskRequestDto;
import ru.npopov.taskmanager.dto.TaskResponseDto;
import ru.npopov.taskmanager.service.TaskService;

import java.util.List;

@RequestMapping("/api/v1/tasks")
@RestController
@Slf4j
@RequiredArgsConstructor
@Tag(
      name = "Task Controller",
      description = "Контроллер для управления задачами"
)
public class TaskController {

    private final TaskService taskService;

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    @Operation(
            summary = "Создание задачи",
            description = "Позволяет создавать задачу"
    )
    public TaskResponseDto create(@RequestBody TaskRequestDto requestDto) {
        log.info("Received request of creating task with name: {}", requestDto.name());
        return taskService.create(requestDto);
    }

    @GetMapping
    @Operation(
            summary = "Получение списка задач",
            description = "Позволяет получать список всех задач"
    )
    public List<TaskResponseDto> get() {
        log.info("Received request of getting all tasks");
        return taskService.get();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    @Operation(
            summary = "Удаление задачи",
            description = "Позволяет удалять задачу"
    )
    public void delete(
            @PathVariable("id") @Parameter(description = "Идентификатор задачи") Long id
    ) {
        taskService.delete(id);
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Обновление задачи",
            description = "Позволяет обновлять задачу"
    )
    public TaskResponseDto update(
            @PathVariable("id") @Parameter(description = "Идентификатор задачи") Long id,
            @RequestBody TaskRequestDto requestDto
    ) {
        log.info("Received update request for task with id: {}", id);
        return taskService.update(id, requestDto);
    }
}
