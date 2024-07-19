package com.project.doit.service;

import com.project.doit.dto.ItemDto;
import com.project.doit.entity.ItemEntity;
import com.project.doit.entity.TaskEntity;
import com.project.doit.exception.EmptyFieldException;
import com.project.doit.exception.NotFoundException;
import com.project.doit.factory.ItemFactory;
import com.project.doit.factory.TaskFactory;
import com.project.doit.mapper.ItemMapper;
import com.project.doit.repository.ItemRepository;
import com.project.doit.repository.TaskRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ItemServiceTest {

    @Mock
    private ItemRepository itemRepository;

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private ItemMapper itemMapper;

    @InjectMocks
    private ItemService itemService;

    @Captor
    ArgumentCaptor<Long> taskIdCaptor;

    @Captor
    ArgumentCaptor<ItemDto> itemDtoCaptor;

    @Captor
    ArgumentCaptor<ItemEntity> itemEntityCaptor;

    @Nested
    class Create {

        @Test
        void itemCreatedSuccessfully() {
            //Arrange
            ItemDto itemDto = ItemFactory.sentDto();
            Long taskId = 1L;
            Optional<TaskEntity> task = TaskFactory.createTaskEntity();

            ItemEntity item = ItemFactory.sentEntity(itemDto);
            item.setTask(task.get());
            ItemDto createdItemVerifier = ItemFactory.receivedDto(item);

            when(taskRepository.findById(taskIdCaptor.capture())).thenReturn(task);
            when(itemMapper.converterParaEntity(itemDtoCaptor.capture())).thenReturn(new ItemEntity(itemDto));
            when(itemRepository.save(itemEntityCaptor.capture())).thenReturn(item);
            when(itemMapper.converterParaDto(itemEntityCaptor.capture())).thenReturn(new ItemDto(item));

            //Act
            ItemDto createdItem = itemService.create(itemDto, taskId);

            //Assert
            verify(itemRepository, times(1)).save(itemEntityCaptor.capture());
            assertEquals(taskId, taskIdCaptor.getValue());
            assertEquals(itemDto, itemDtoCaptor.getValue());
            assertEquals(item.getTask(), itemEntityCaptor.getAllValues().getFirst().getTask());
            assertEquals(item, itemEntityCaptor.getAllValues().get(1));
            assertEquals(createdItemVerifier, createdItem);
        }

        @Test
        void shouldSendNotFoundExceptionWhenTaskNotFound() {
            //Arrange
            ItemDto itemDto = ItemFactory.sentDto();
            Long taskId = 2L;
            when(taskRepository.findById(taskIdCaptor.capture())).thenReturn(Optional.empty());

            //Act
            Exception thrown = Assertions.assertThrows(NotFoundException.class, () -> {
                itemService.create(itemDto, taskId);
            });

            //Assert
            assertEquals("Not found exception: task", thrown.getMessage());
            assertEquals(taskIdCaptor.getValue(), taskId);
        }

        @Test
        void shouldSendEmptyFieldExceptionWhenItemDtoIsEmpty() {
            //Arrange
            ItemDto itemDto = ItemFactory.emptyDto();
            Long taskId = 1L;

            //Act
            Exception thrown = Assertions.assertThrows(EmptyFieldException.class, () -> {
                itemService.isEmptyField(itemDto);
                itemService.create(itemDto, taskId);
            });

            //Assert
            assertEquals("Empty field exception: description", thrown.getMessage());
        }
    }

    @Test
    void update() {
    }

    @Test
    void getTaskItems() {
    }

    @Test
    void delete() {
    }
}