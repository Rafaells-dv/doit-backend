package com.project.doit.mapper;

import com.project.doit.dto.ItemDto;
import com.project.doit.entity.ItemEntity;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ItemMapper {

    public ItemDto converterParaDto(ItemEntity item) {
        return new ItemDto(item);
    }

    public ItemEntity converterParaEntity(ItemDto itemDto) {
        return new ItemEntity(itemDto);
    }
}
