package com.project.doit.factory;

import com.project.doit.dto.ItemDto;
import com.project.doit.entity.ItemEntity;

public class ItemFactory {

    public static ItemDto sentDto(){
        return new ItemDto(1L, "Item");
    }

    public static ItemDto emptyDto() {
        return new ItemDto(1L, "");
    }

    public static ItemEntity sentEntity(ItemDto item){
        return new ItemEntity(item);
    }

    public static ItemDto receivedDto(ItemEntity item){
        return new ItemDto(item);
    }
}
