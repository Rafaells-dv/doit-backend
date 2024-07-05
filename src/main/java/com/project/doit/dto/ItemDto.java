package com.project.doit.dto;

import com.project.doit.entity.ItemEntity;
import lombok.Data;
import org.springframework.beans.BeanUtils;

@Data
public class ItemDto {

    private Long id;

    private String description;

    public ItemDto() {super(); }

    public ItemDto(ItemEntity itemEntity) {
        BeanUtils.copyProperties(itemEntity, this);
    }
}
