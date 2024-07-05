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

    @PostMapping("/newtask")
    public ResponseEntity<?> addTask(@RequestBody TaskDto taskDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(taskService.create(taskDto));
    }

    @PutMapping("/att/{taskId}")
    public ResponseEntity<?> updateTask(@RequestBody TaskDto taskDto, @PathVariable Long taskId) {
        return ResponseEntity.status(HttpStatus.CREATED).body(taskService.update(taskDto, taskId));
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllTasks() {
        return ResponseEntity.ok().body(taskService.findAllTasks());
    }

    @DeleteMapping("/delete/{taskId}")
    public ResponseEntity<?> deleteTask(@PathVariable Long taskId) {
        taskService.delete(taskId);
        return ResponseEntity.ok().body("Tarefa excluída com sucesso");
    }
}
