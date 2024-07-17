package com.project.doit.dto;

import com.project.doit.entity.TaskEntity;

public record TaskDto(Long id, String title) {

    public TaskDto(TaskEntity task){
        this(task.getId(), task.getTitle());
    }
}
