package com.pdcollab.tasks.service;

import com.pdcollab.tasks.model.Task;
import com.pdcollab.tasks.repository.TaskRepository;
import com.pdcollab.users.model.User;
import com.pdcollab.users.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    @Transactional
    public Task createTaskForUser(long userId, Task task) {
        User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User not found!!!"));
        task.setUser(user);
        return taskRepository.save(task);
    }

    public List<Task> getTaskByUser(long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User not found!!!"));
        return user.getTasks();
    }

    public Task updateTask(long id, Task task) {
        Task existingTask = taskRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Task not found!!!!"));
        existingTask.setTitle(task.getTitle());
        existingTask.setContent(task.getContent());
        return taskRepository.save(existingTask);
    }
}
