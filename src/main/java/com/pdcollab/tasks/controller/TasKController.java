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
            return new ResponseEntity<>(tasks, HttpStatus.FOUND);
        } catch (Exception E) {
            System.out.println(E);
            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        }
    }

    @PostMapping("/{userId}/users")
    public ResponseEntity<Task> createTask(@PathVariable long userId, @RequestBody Task task) {
        try {
            Task createdTask = taskService.createTaskForUser(userId, task);
            return new ResponseEntity<>(createdTask, HttpStatus.OK);
        } catch (Exception E) {
            System.out.println(E);
            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable long id, @RequestBody Task task) {
        try {
            Task updatedTask = taskService.updateTask(id, task);
            return new ResponseEntity<>(updatedTask, HttpStatus.OK);
        } catch (Exception E) {
            System.out.println(E);
            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        }
    }
}
