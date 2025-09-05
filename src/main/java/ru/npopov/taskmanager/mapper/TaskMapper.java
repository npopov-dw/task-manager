package ru.npopov.taskmanager.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import ru.npopov.taskmanager.dto.TaskRequestDto;
import ru.npopov.taskmanager.dto.TaskResponseDto;
import ru.npopov.taskmanager.persistence.entity.Task;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface TaskMapper {

    Task mapToEntity(TaskRequestDto dto);

    TaskResponseDto mapToDto(Task entity);

    List<TaskResponseDto> mapToDto(List<Task> entities);
}
