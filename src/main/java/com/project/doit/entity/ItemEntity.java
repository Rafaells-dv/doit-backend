package com.project.doit.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.doit.dto.ItemDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.beans.BeanUtils;

@Data
@Entity
@Table(name="item")
public class ItemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(min = 2, max = 50)
    private String description;

    @ManyToOne
    @JoinColumn(name="task_id")
    @JsonIgnore
    private TaskEntity task;

    public ItemEntity() {super();}

    public ItemEntity(ItemDto itemDto) {
        BeanUtils.copyProperties(itemDto, this);
    }
}
