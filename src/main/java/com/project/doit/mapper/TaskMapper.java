package com.project.doit.mapper;

import com.project.doit.dto.TaskDto;
import com.project.doit.entity.TaskEntity;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TaskMapper {

    public TaskDto converterParaDto(TaskEntity task) {
        return new TaskDto(task);
    }

    public TaskEntity converterParaEntity(TaskDto taskDto) {
        return new TaskEntity(taskDto);
    }
}
