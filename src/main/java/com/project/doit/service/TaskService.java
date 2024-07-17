package com.project.doit.service;

import com.project.doit.dto.TaskDto;
import com.project.doit.entity.TaskEntity;
import com.project.doit.exception.EmptyFieldException;
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
        if (taskDto.getTitle() == null || taskDto.getTitle().isEmpty()) {
            throw new EmptyFieldException("Título deve ser preenchido.");
        }

        TaskEntity taskEntity = taskMapper.converterParaEntity(taskDto);
        taskRepository.save(taskEntity);

        return taskMapper.converterParaDto(taskEntity);
    }

    public TaskDto update(TaskDto taskDto, Long id) {
        if (taskDto.getTitle() == null || taskDto.getTitle().isEmpty()) {
            throw new EmptyFieldException();
        }

        Optional<TaskEntity> optionalTask = taskRepository.findById(id);
        if (optionalTask.isPresent()) {
            TaskEntity taskSelected = optionalTask.get();

            taskSelected.setTitle(taskDto.getTitle());

            taskRepository.save(taskSelected);
            return taskMapper.converterParaDto(taskSelected);
        } else {
            return new TaskDto();
        }
    }

    public List<TaskDto> findAllTasks() {
        List<TaskEntity> taskEntities = taskRepository.findAll();
        return taskEntities.stream().map(taskMapper::converterParaDto).collect(Collectors.toList());
    }

    public void delete(Long id) {
        taskRepository.deleteById(id);
    }
}
