package com.pdcollab.tasks.controller;

import com.pdcollab.tasks.model.Task;
import com.pdcollab.tasks.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TasKController {
    private final TaskService taskService;

    @Autowired
    public TasKController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    public ResponseEntity<List<Task>> getAllTasks() {
        return new ResponseEntity<>(taskService.getAllTasks(), HttpStatus.OK);
    }

    @GetMapping("/{userId}/users")
    public ResponseEntity<List<Task>> getTaskByUser(@PathVariable long userId) {
        try {
            List<Task> tasks = taskService.getTaskByUser(userId);
            return new ResponseEntity<>(tasks, HttpStatus.OK);
        } catch (Exception E) {
            System.out.println(E);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/{userId}/users")
    public ResponseEntity<Task> createTask(@PathVariable long userId, @RequestParam String title, @RequestParam String content, @RequestParam List<String> tags) {
        try {
            Task createdTask = taskService.createTaskForUser(userId, title, content, tags);
            return new ResponseEntity<>(createdTask, HttpStatus.OK);
        } catch (Exception E) {
            System.out.println(E);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable long id, @RequestParam String title, @RequestParam String content) {
        try {
            Task updatedTask = taskService.updateTask(id, title, content);
            return new ResponseEntity<>(updatedTask, HttpStatus.OK);
        } catch (Exception E) {
            System.out.println(E);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
