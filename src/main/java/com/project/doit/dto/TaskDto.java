package com.project.doit.dto;

import com.project.doit.entity.TaskEntity;
import lombok.Data;
import org.springframework.beans.BeanUtils;

@Data
public class TaskDto {

    private Long id;

    private String title;

    public TaskDto() {super(); }

    public TaskDto(TaskEntity task) {
        BeanUtils.copyProperties(task, this);
    }
}
