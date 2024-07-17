package com.project.doit.dto;

import com.project.doit.entity.ItemEntity;

public record ItemDto(Long id, String description) {

    public ItemDto(ItemEntity item){
        this(item.getId(), item.getDescription());
    }
}
