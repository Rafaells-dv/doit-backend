package com.project.doit.service;

import com.project.doit.dto.ItemDto;
import com.project.doit.entity.ItemEntity;
import com.project.doit.entity.TaskEntity;
import com.project.doit.exception.EmptyFieldException;
import com.project.doit.mapper.ItemMapper;
import com.project.doit.repository.ItemRepository;
import com.project.doit.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ItemService {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private ItemMapper itemMapper;

    public ItemDto create(ItemDto itemDto, Long taskId) {
        if (itemDto.getDescription() == null || itemDto.getDescription().isEmpty()) {
            throw new EmptyFieldException("Item deve ser preenchido.");
        }

        TaskEntity taskEntity =  taskRepository.findById(taskId).get();

        ItemEntity itemEntity = itemMapper.converterParaEntity(itemDto);
        itemEntity.setTask(taskEntity);

        itemRepository.save(itemEntity);

        return itemMapper.converterParaDto(itemEntity);
    }

    public ItemDto update(ItemDto itemDto, Long itemId) {
        if (itemDto.getDescription() == null || itemDto.getDescription().isEmpty()) {
            throw new EmptyFieldException();
        }
        ItemEntity updateItem = itemRepository.findById(itemId).get();

        updateItem.setDescription(itemDto.getDescription());

        itemRepository.save(updateItem);
        return itemMapper.converterParaDto(updateItem);
    }

    public List<ItemDto> getTaskItems(Long taskId) {
        List<ItemEntity> items = itemRepository.findAllBytaskId(taskId);
        return items.stream().map(itemMapper::converterParaDto).collect(Collectors.toList());
    }

    public void delete(Long itemId) {
        ItemEntity itemEntity = itemRepository.findById(itemId).get();
        itemRepository.delete(itemEntity);
    }
}
