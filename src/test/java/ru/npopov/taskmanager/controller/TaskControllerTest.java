package ru.npopov.taskmanager.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.npopov.taskmanager.dto.TaskRequestDto;
import ru.npopov.taskmanager.dto.TaskResponseDto;
import ru.npopov.taskmanager.service.TaskService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.npopov.taskmanager.util.TaskTestData.DEFAULT_ID;
import static ru.npopov.taskmanager.util.TaskTestData.TASK_URL_TEMPLATE;
import static ru.npopov.taskmanager.util.TaskTestData.createTaskRequestDto;
import static ru.npopov.taskmanager.util.TaskTestData.createTaskRequestDtoToUpdate;
import static ru.npopov.taskmanager.util.TaskTestData.createTaskResponseDto;
import static ru.npopov.taskmanager.util.TaskTestData.createTaskResponseDtos;
import static ru.npopov.taskmanager.util.TaskTestData.createUpdatedTaskResponseDto;

@WebMvcTest(controllers = TaskController.class)
public class TaskControllerTest {

    @MockitoBean
    private TaskService taskService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testCreate_shouldReturn201AndCorrectJson() throws Exception {
        // Arrange
        TaskRequestDto requestDto = createTaskRequestDto();
        TaskResponseDto responseDto = createTaskResponseDto();
        when(taskService.create(requestDto)).thenReturn(responseDto);

        // Act & Assert
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post(TASK_URL_TEMPLATE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isCreated())
                .andReturn();

        String actualResult = mvcResult.getResponse().getContentAsString();
        String expectedResult = objectMapper.writeValueAsString(responseDto);
        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void testDelete_shouldReturn204() throws Exception {
        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.delete(String.format("%s/%s", TASK_URL_TEMPLATE, DEFAULT_ID)))
                .andExpect(status().isNoContent());
        verify(taskService, times(1)).delete(DEFAULT_ID);
    }

    @Test
    public void testGet_shouldReturn200AndCorrectJson() throws Exception {
        // Arrange
        List<TaskResponseDto> responseDtos = createTaskResponseDtos();
        when(taskService.get()).thenReturn(responseDtos);

        // Act & Assert
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(TASK_URL_TEMPLATE))
                .andExpect(status().isOk())
                .andReturn();

        String expectedResult = objectMapper.writeValueAsString(createTaskResponseDtos());
        String actualResult = mvcResult.getResponse().getContentAsString();
        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void testUpdate_shouldReturn200AndCorrectJson() throws Exception {
        // Arrange
        TaskRequestDto requestDto = createTaskRequestDtoToUpdate();
        TaskResponseDto responseDto = createUpdatedTaskResponseDto();
        when(taskService.update(DEFAULT_ID, requestDto)).thenReturn(responseDto);

        // Act & Assert
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.put(String.format("%s/%s", TASK_URL_TEMPLATE, DEFAULT_ID))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isOk())
                .andReturn();
        String actualResult = mvcResult.getResponse().getContentAsString();
        String expectedResult = objectMapper.writeValueAsString(createUpdatedTaskResponseDto());
        assertEquals(expectedResult, actualResult);
    }
}
