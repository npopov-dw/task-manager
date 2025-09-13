package ru.npopov.taskmanager.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.npopov.taskmanager.dto.TaskRequestDto;
import ru.npopov.taskmanager.dto.TaskResponseDto;
import ru.npopov.taskmanager.mapper.TaskMapper;
import ru.npopov.taskmanager.persistence.entity.Task;
import ru.npopov.taskmanager.persistence.repository.TaskRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static ru.npopov.taskmanager.util.TaskTestData.DEFAULT_ID;
import static ru.npopov.taskmanager.util.TaskTestData.createTask;
import static ru.npopov.taskmanager.util.TaskTestData.createTaskRequestDto;
import static ru.npopov.taskmanager.util.TaskTestData.createTaskRequestDtoToUpdate;
import static ru.npopov.taskmanager.util.TaskTestData.createTaskResponseDto;
import static ru.npopov.taskmanager.util.TaskTestData.createTaskResponseDtos;
import static ru.npopov.taskmanager.util.TaskTestData.createTaskToSaving;
import static ru.npopov.taskmanager.util.TaskTestData.createTaskToUpdate;
import static ru.npopov.taskmanager.util.TaskTestData.createTasks;
import static ru.npopov.taskmanager.util.TaskTestData.createUpdatedTask;
import static ru.npopov.taskmanager.util.TaskTestData.createUpdatedTaskResponseDto;

@ExtendWith(MockitoExtension.class)
public class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private TaskMapper taskMapper;

    @InjectMocks
    private TaskService taskService;

    @Test
    public void testCreate_Success() {
        // Arrange
        TaskRequestDto requestDto = createTaskRequestDto();
        Task taskToSaving = createTaskToSaving();
        Task task = createTask();
        TaskResponseDto responseDto = createTaskResponseDto();
        when(taskMapper.mapToEntity(requestDto)).thenReturn(taskToSaving);
        when(taskRepository.save(createTaskToSaving())).thenReturn(task);
        when(taskMapper.mapToDto(createTask())).thenReturn(responseDto);

        // Act
        TaskResponseDto receivedResponseDto = taskService.create(requestDto);

        // Assert
        TaskResponseDto expectedResponseDto = createTaskResponseDto();
        assertEquals(expectedResponseDto, receivedResponseDto);
    }

    @Test
    public void testGet_Success() {
        // Arrange
        List<Task> tasks = createTasks();
        List<TaskResponseDto> responseDtos = createTaskResponseDtos();
        when(taskRepository.findAll()).thenReturn(tasks);
        when(taskMapper.mapToDto(createTasks())).thenReturn(responseDtos);

        // Act
        List<TaskResponseDto> receivedDtos = taskService.get();

        // Assert
        List<TaskResponseDto> expectedResponseDtos = createTaskResponseDtos();
        assertEquals(expectedResponseDtos, receivedDtos);
    }

    @Test
    public void testDelete_Success() {
        // Act
        taskService.delete(DEFAULT_ID);

        // Assert
        verify(taskRepository, times(1)).deleteById(DEFAULT_ID);
    }

    @Test
    public void testUpdate_Success() {
        // Arrange
        TaskRequestDto requestDto = createTaskRequestDtoToUpdate();
        Task taskToUpdate = createTaskToUpdate();
        Task existsingTask = createTask();
        when(taskMapper.mapToEntity(requestDto)).thenReturn(taskToUpdate);
        when(taskRepository.getReferenceById(DEFAULT_ID)).thenReturn(existsingTask);
        when(taskRepository.save(createUpdatedTask())).thenReturn(createUpdatedTask());
        when(taskMapper.mapToDto(createUpdatedTask())).thenReturn(createUpdatedTaskResponseDto());

        // Act
        TaskResponseDto receivedResponseDto = taskService.update(DEFAULT_ID, requestDto);

        // Assert
        TaskResponseDto expectedResponseDto = createUpdatedTaskResponseDto();
        assertEquals(expectedResponseDto, receivedResponseDto);
    }
}
