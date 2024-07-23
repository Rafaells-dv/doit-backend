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
import jakarta.persistence.Entity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
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
    ArgumentCaptor<Long> itemIdCaptor;

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

            when(taskRepository.findById(taskIdCaptor.capture())).thenReturn(task);
            when(itemMapper.converterParaEntity(itemDtoCaptor.capture())).thenReturn(new ItemEntity(itemDto));

            ItemEntity item = ItemFactory.sentEntity(itemDto);
            item.setTask(task.get());
            ItemDto createdItemVerifier = ItemFactory.receivedDto(item);

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

    @Nested
    class Update {

        @Test
        void itemUpdatedSuccessfully() {
            //Arrange
            ItemDto itemDto = ItemFactory.sentDto();
            Long itemId = itemDto.id();
            Optional<ItemEntity> updateThis = Optional.of(ItemFactory.createEntity(1L, "Update this"));

            when(itemRepository.findById(itemIdCaptor.capture())).thenReturn(updateThis);

            updateThis.get().setDescription(itemDto.description());
            ItemEntity updatedItem = updateThis.get();
            ItemDto updatedItemDto = ItemFactory.receivedDto(updatedItem);

            when(itemRepository.save(itemEntityCaptor.capture())).thenReturn(updatedItem);
            when(itemMapper.converterParaDto(itemEntityCaptor.capture())).thenReturn(updatedItemDto);

            //Act
            itemService.update(itemDto, itemId);

            //Assert
            assertEquals(itemDto, updatedItemDto);
            assertEquals(itemId, updateThis.get().getId());
            assertEquals(itemId, itemIdCaptor.getValue());
            assertEquals(itemEntityCaptor.getAllValues().getFirst(), updatedItem);
            assertEquals(itemEntityCaptor.getAllValues().getLast(), updatedItem);
        }

        @Test
        void shouldSendNotFoundExceptionWhenItemNotFound() {
            ItemDto itemDto = ItemFactory.sentDto();
            Long taskId = 2L;

            when(itemRepository.findById(taskIdCaptor.capture())).thenReturn(Optional.empty());

            Exception thrown = Assertions.assertThrows(NotFoundException.class, () -> {
                itemService.update(itemDto, taskId);
            });

            assertEquals("Not found exception: item", thrown.getMessage());
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

    @Nested
    class Get {

        @Test
        void getTaskItemsSuccessfully() {
            //Arrange
            Long taskId = 1L;
            List<ItemEntity> itemList = ItemFactory.entityItemList();
            List<ItemDto> responseList = ItemFactory.dtoItemList();

            when(itemRepository.findAllBytaskId(taskIdCaptor.capture())).thenReturn(itemList);

            for (ItemDto itemDto : responseList) {
                when(itemMapper.converterParaDto(itemEntityCaptor.capture())).thenReturn(itemDto);
                when(itemMapper.converterParaEntity(itemDto)).thenReturn(itemList.get(responseList.indexOf(itemDto)));
            }

            //Act
            itemService.getTaskItems(taskId);

            //Assert
            assertEquals(responseList.size(), itemList.size());
            assertEquals(taskId, taskIdCaptor.getValue());
            for (ItemEntity itemEntity : itemList) {
                assertEquals(itemEntityCaptor.getAllValues().get(itemList.indexOf(itemEntity) + 1), itemEntity);
                assertEquals(itemEntity, itemMapper.converterParaEntity(responseList.get(itemList.indexOf(itemEntity))));
            }
        }

        @Test
        void shouldSendNotFoundExceptionWhenTaskNotFound() {
            Long taskId = 2L;
            when(taskRepository.findById(taskIdCaptor.capture())).thenReturn(Optional.empty());
            Exception thrown = Assertions.assertThrows(NotFoundException.class, () -> {
                itemService.getTaskItems(taskId);
            });
            assertEquals("Not found exception: task", thrown.getMessage());
            assertEquals(taskIdCaptor.getValue(), taskId);
        }
    }

    @Nested
    class Delete {

        @Test
        void deleteItemSuccessfully() {
            ItemEntity itemEntity = ItemFactory.createEntity(1L, "Delete this");
            when(itemRepository.findById(itemIdCaptor.capture())).thenReturn(Optional.of(itemEntity));
            doNothing().when(itemRepository).delete(itemEntityCaptor.capture());

            itemService.delete(itemEntity.getId());

            verify(itemRepository, times(1)).delete(itemEntityCaptor.getValue());
            assertEquals(itemEntity, itemEntityCaptor.getAllValues().getFirst());
        }

        @Test
        void shouldSendNotFoundWhenItemNotFound() {
            Long itemId = 1L;

            when(itemRepository.findById(itemIdCaptor.capture())).thenReturn(Optional.empty());

            Exception thrown = Assertions.assertThrows(NotFoundException.class, () -> {
                itemService.delete(itemId);
            });

            assertEquals("Not found exception: item", thrown.getMessage());
        }
    }

    @Test
    void emptyFieldTrue() {
        ItemDto itemDto = ItemFactory.emptyDto();

        boolean response = itemService.isEmptyField(itemDto);

        assertTrue(response);
    }

    @Test
    void emptyFieldFalse() {
        ItemDto itemDto = ItemFactory.sentDto();

        boolean response = itemService.isEmptyField(itemDto);

        assertFalse(response);
    }
}