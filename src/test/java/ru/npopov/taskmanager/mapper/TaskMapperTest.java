package ru.npopov.taskmanager.mapper;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.npopov.taskmanager.dto.TaskRequestDto;
import ru.npopov.taskmanager.dto.TaskResponseDto;
import ru.npopov.taskmanager.persistence.entity.Task;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.npopov.taskmanager.util.TaskTestData.*;

@ExtendWith(MockitoExtension.class)
public class TaskMapperTest {

    private TaskMapper taskMapper = Mappers.getMapper(TaskMapper.class);

    @Test
    public void testMapToEntity_Success() {
        // Arrange
        TaskRequestDto requestDto = createTaskRequestDto();

        // Act
        Task receivedTask = taskMapper.mapToEntity(requestDto);

        // Assert
        Task expectedTask = createTaskToSaving();
        assertEquals(expectedTask, receivedTask);
    }

    @Test
    public void testMapToDto_Success() {
        // Arrange
        Task task = createTask();

        // Act
        TaskResponseDto receivedTaskResponseDto = taskMapper.mapToDto(task);

        // Assert
        TaskResponseDto expectedTaskResponseDto = createTaskResponseDto();
        assertThat(receivedTaskResponseDto)
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(expectedTaskResponseDto);
    }

    @Test
    public void testMapToDto_ListSuccess() {
        // Arrange
        List<Task> tasks = createTasks();

        // Act
        List<TaskResponseDto> receivedResponseDtos = taskMapper.mapToDto(tasks);

        // Assert
        List<TaskResponseDto> expectedResponseDtos = createTaskResponseDtos();
        assertThat(receivedResponseDtos)
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(expectedResponseDtos);
    }
}
