package ru.npopov.taskmanager.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import ru.npopov.taskmanager.dto.TaskRequestDto;
import ru.npopov.taskmanager.dto.TaskResponseDto;
import ru.npopov.taskmanager.persistence.entity.Task;
import ru.npopov.taskmanager.persistence.repository.TaskRepository;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static ru.npopov.taskmanager.util.TaskTestData.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(
        properties = {
                "spring.datasource.url=jdbc:tc:postgresql:17.4:///test",
                "spring.cloud.discovery.enabled=false"
        }
)
public class TaskControllerIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private TaskRepository taskRepository;

    @Test
    @Sql(scripts = "classpath:testdata/clear_tasks_test_data.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void testCreate_Success() {
        // Arrange
        TaskRequestDto requestDto = createTaskRequestDto();
        HttpEntity<Object> entity = new HttpEntity<>(requestDto);

        // Act
        ResponseEntity<TaskResponseDto> response = restTemplate.exchange(
                TASK_URL_TEMPLATE,
                HttpMethod.POST,
                entity,
                TaskResponseDto.class
        );

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, response.getHeaders().getContentType());
        TaskResponseDto expectedResponseDto = createTaskResponseDto();
        TaskResponseDto actualResponseDto = response.getBody();
        assertNotNull(actualResponseDto);
        assertThat(actualResponseDto)
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(expectedResponseDto);
        assertNotNull(actualResponseDto.id());

        List<Task> taskList = taskRepository.findAll();
        assertEquals(1, taskList.size());
        Task actualTask = taskList.getFirst();
        assertNotNull(actualTask);
        assertNotNull(actualTask);
        Task expectedTask = createTaskToSaving();
        assertThat(actualTask)
                .usingRecursiveComparison()
                .ignoringFields("id", "createdAt", "updatedAt")
                .isEqualTo(expectedTask);
        assertNotNull(actualTask.getId());
        assertNotNull(actualTask.getCreatedAt());
        assertNotNull(actualTask.getUpdatedAt());
    }

    @Test
    @Sql(scripts = "classpath:testdata/prepare_tasks_for_test.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:testdata/clear_tasks_test_data.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void testDelete_Success() {
        // Act & Assert
        Task taskBeforeDelete = taskRepository.getReferenceById(DEFAULT_ID);
        assertNotNull(taskBeforeDelete);

        ResponseEntity<String> response = restTemplate.exchange(
                String.format("%s/%s", TASK_URL_TEMPLATE, DEFAULT_ID),
                HttpMethod.DELETE,
                null,
                String.class
        );
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        Optional<Task> taskAfterDelete = taskRepository.findById(DEFAULT_ID);
        assertTrue(taskAfterDelete.isEmpty());
        List<Task> tasks = taskRepository.findAll();
        assertEquals(1, tasks.size());
    }

    @Test
    @Sql(scripts = "classpath:testdata/prepare_tasks_for_test.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:testdata/clear_tasks_test_data.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void testUpdate_Success() {
        // Arrange
        TaskRequestDto requestDto = createTaskRequestDtoToUpdate();
        HttpEntity<Object> entity = new HttpEntity<>(requestDto);

        // Act
        ResponseEntity<TaskResponseDto> response = restTemplate.exchange(
                String.format("%s/%s", TASK_URL_TEMPLATE, DEFAULT_ID),
                HttpMethod.PUT,
                entity,
                TaskResponseDto.class
        );

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        TaskResponseDto expectedResponseDto = createUpdatedTaskResponseDto();
        assertEquals(expectedResponseDto, response.getBody());

        Task actualTask = taskRepository.findById(DEFAULT_ID).orElse(null);
        Task expectedTask = createUpdatedTask();
        assertNotNull(actualTask);
        assertThat(actualTask)
                .usingRecursiveComparison()
                .ignoringFields("id", "createdAt", "updatedAt")
                .isEqualTo(expectedTask);
        assertEquals(DEFAULT_ID, actualTask.getId());
        assertNotNull(actualTask.getCreatedAt());
        assertTrue(actualTask.getCreatedAt().isBefore(actualTask.getUpdatedAt()));
    }

    @Test
    @Sql(scripts = "classpath:testdata/prepare_tasks_for_test.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:testdata/clear_tasks_test_data.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void testGet_Success() {
        // Act
        ResponseEntity<List<TaskResponseDto>> response = restTemplate.exchange(
                TASK_URL_TEMPLATE,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {}
        );

        // Assert
        List<TaskResponseDto> actualTaskResponseDtos = response.getBody();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(actualTaskResponseDtos);
        assertEquals(2, actualTaskResponseDtos.size());
    }
}
