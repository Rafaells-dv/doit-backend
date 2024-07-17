package com.project.doit.controller;

import com.project.doit.dto.TaskDto;
import com.project.doit.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @PostMapping()
    public ResponseEntity<?> addTask(@RequestBody TaskDto taskDto) {
        TaskDto newTask = taskService.create(taskDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(newTask);
    }

    @PutMapping()
    public ResponseEntity<?> updateTask(@RequestBody TaskDto taskDto, @RequestParam Long taskId) {
        return ResponseEntity.status(HttpStatus.CREATED).body(taskService.update(taskDto, taskId));
    }

    @GetMapping()
    public ResponseEntity<?> getAllTasks() {
        return ResponseEntity.ok().body(taskService.findAllTasks());
    }

    @DeleteMapping()
    public ResponseEntity<?> deleteTask(@RequestParam Long taskId) {
        taskService.delete(taskId);
        return ResponseEntity.ok().body("Tarefa excluída com sucesso");
    }
}
