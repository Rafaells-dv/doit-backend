package com.project.doit.entity;

import com.project.doit.dto.TaskDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.beans.BeanUtils;

@Data
@Entity
@Table(name="task")
public class TaskEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(min=3, max=100)
    private String title;

    public TaskEntity() {super(); }

    public TaskEntity(TaskDto taskDto) {
        BeanUtils.copyProperties(taskDto, this);
    }
}
