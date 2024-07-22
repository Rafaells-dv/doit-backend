package com.project.doit.factory;

import com.project.doit.dto.ItemDto;
import com.project.doit.dto.TaskDto;
import com.project.doit.entity.ItemEntity;
import com.project.doit.entity.TaskEntity;

import java.util.ArrayList;
import java.util.List;

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

    public static ItemEntity createEntity(Long id, String name) {
        ItemEntity item = new ItemEntity(new ItemDto(id, name));
        TaskEntity task = new TaskEntity(new TaskDto(1L, "Task"));
        item.setTask(task);
        return item;
    }

    public static List<ItemEntity> entityItemList(){
        ItemEntity item1 = new ItemEntity(new ItemDto(1L, "Item 1"));
        ItemEntity item2 = new ItemEntity(new ItemDto(2L, "Item 2"));

        List<ItemEntity> list = new ArrayList<ItemEntity>();

        list.add(item1);
        list.add(item2);

        return list;
    }

    public static List<ItemDto> dtoItemList(){
        ItemDto item1 = new ItemDto(1L, "Item 1");
        ItemDto item2 = new ItemDto(2L, "Item 2");

        List<ItemDto> list = new ArrayList<ItemDto>();

        list.add(item1);
        list.add(item2);

        return list;
    }

    public static ItemDto receivedDto(ItemEntity item){
        return new ItemDto(item);
    }
}
