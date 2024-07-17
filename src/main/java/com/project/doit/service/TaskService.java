package com.project.doit.service;

import com.project.doit.dto.TaskDto;
import com.project.doit.entity.TaskEntity;
import com.project.doit.exception.EmptyFieldException;
import com.project.doit.exception.NotFoundException;
import com.project.doit.mapper.TaskMapper;
import com.project.doit.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private TaskMapper taskMapper;

    public TaskDto create(TaskDto taskDto) {
        if (taskDto.title() == null || taskDto.title().isEmpty()) {
            throw new EmptyFieldException("title");
        }

        TaskEntity taskEntity = taskMapper.converterParaEntity(taskDto);
        taskRepository.save(taskEntity);

        return taskMapper.converterParaDto(taskEntity);
    }

    public TaskDto update(TaskDto taskDto, Long id) {
        if (taskDto.title() == null || taskDto.title().isEmpty()) {
            throw new EmptyFieldException("title");
        }

        Optional<TaskEntity> taskSelected = taskRepository.findById(id);
        if (taskSelected.isPresent()) {

            taskSelected.get().setTitle(taskDto.title());

            taskRepository.save(taskSelected.get());
            return taskMapper.converterParaDto(taskSelected.get());
        } else {
            throw new NotFoundException("task");
        }
    }

    public List<TaskDto> findAllTasks() {
        List<TaskEntity> taskEntities = taskRepository.findAll();
        return taskEntities.stream().map(taskMapper::converterParaDto).collect(Collectors.toList());
    }

    public void delete(Long id) {
        Optional<TaskEntity> optionalTask = taskRepository.findById(id);
        if (optionalTask.isPresent()) {
            taskRepository.deleteById(id);
        } else {
            throw new NotFoundException("task");
        }

    }
}
