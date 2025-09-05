package ru.npopov.taskmanager.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.npopov.taskmanager.dto.TaskRequestDto;
import ru.npopov.taskmanager.dto.TaskResponseDto;
import ru.npopov.taskmanager.mapper.TaskMapper;
import ru.npopov.taskmanager.persistence.entity.Task;
import ru.npopov.taskmanager.persistence.repository.TaskRepository;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;

    public TaskResponseDto create(TaskRequestDto requestDto) {
        Task savedTask = taskRepository.save(taskMapper.mapToEntity(requestDto));
        log.info("Saved task with name: {}", savedTask.getName());
        return taskMapper.mapToDto(savedTask);
    }

    public List<TaskResponseDto> get() {
        List<Task> tasks = taskRepository.findAll();
        log.info("Received {} tasks from db", tasks.size());
        return taskMapper.mapToDto(tasks);
    }

    public void delete(Long id) {
        taskRepository.deleteById(id);
        log.info("Task with id: {} deleted", id);
    }

    public TaskResponseDto update(Long id, TaskRequestDto requestDto) {
        Task newTask = taskMapper.mapToEntity(requestDto);
        Task task = taskRepository.getReferenceById(id);
        task.setName(newTask.getName());
        task.setDescription(newTask.getDescription());
        task.setDateExec(newTask.getDateExec());

        TaskResponseDto responseDto = taskMapper.mapToDto(taskRepository.save(task));
        log.info("Task with id: {} updated", responseDto.id());
        return responseDto;
    }
}
