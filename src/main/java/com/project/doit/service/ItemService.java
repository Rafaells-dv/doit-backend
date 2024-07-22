package com.project.doit.service;

import com.project.doit.dto.ItemDto;
import com.project.doit.entity.ItemEntity;
import com.project.doit.entity.TaskEntity;
import com.project.doit.exception.EmptyFieldException;
import com.project.doit.exception.NotFoundException;
import com.project.doit.mapper.ItemMapper;
import com.project.doit.repository.ItemRepository;
import com.project.doit.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ItemService {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private ItemMapper itemMapper;

    public ItemDto create(ItemDto itemDto, Long taskId){
        if (isEmptyField(itemDto)) {
            throw new EmptyFieldException("description");
        }

        Optional<TaskEntity> taskEntity =  taskRepository.findById(taskId);

        if (taskEntity.isPresent()) {

            ItemEntity itemEntity = itemMapper.converterParaEntity(itemDto);
            itemEntity.setTask(taskEntity.get());
            itemRepository.save(itemEntity);

            return itemMapper.converterParaDto(itemEntity);
        } else {
            throw new NotFoundException("task");
        }
    }

    public ItemDto update(ItemDto itemDto, Long itemId) {
        if (isEmptyField(itemDto)) {
            throw new EmptyFieldException("description");
        }

        Optional<ItemEntity> updateItem = itemRepository.findById(itemId);

        if (updateItem.isPresent()) {

            updateItem.get().setDescription(itemDto.description());
            itemRepository.save(updateItem.get());

            return itemMapper.converterParaDto(updateItem.get());

        } else {
            throw new NotFoundException("item");
        }
    }

    public List<ItemDto> getTaskItems(Long taskId) {
        Optional<TaskEntity> taskEntity =  taskRepository.findById(taskId);

        if (taskEntity.isEmpty()) {
            throw new NotFoundException("task");
        }

        List<ItemEntity> items = itemRepository.findAllBytaskId(taskId);
        return items.stream().map(itemMapper::converterParaDto).collect(Collectors.toList());
    }

    public void delete(Long itemId) {
        Optional<ItemEntity> itemEntity = itemRepository.findById(itemId);

        if (itemEntity.isPresent()) {
            itemRepository.delete(itemEntity.get());
        } else {
            throw new NotFoundException("item");
        }
    }

    public boolean isEmptyField(ItemDto itemDto){
        return itemDto.description() == null || itemDto.description().isEmpty();
    }
}
