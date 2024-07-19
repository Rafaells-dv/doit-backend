package com.project.doit.factory;

import com.project.doit.dto.TaskDto;
import com.project.doit.entity.TaskEntity;

import java.util.Optional;

public class TaskFactory {

    public static Optional<TaskEntity> createTaskEntity() {
        TaskEntity taskEntity = new TaskEntity(new TaskDto(1L, "Tarefa"));
        return Optional.of(taskEntity);
    }
}
