package com.pdcollab.tasks.service;

import com.pdcollab.learnings.model.Tag;
import com.pdcollab.learnings.repository.TagRepository;
import com.pdcollab.tasks.model.Task;
import com.pdcollab.tasks.repository.TaskRepository;
import com.pdcollab.auth.model.User;
import com.pdcollab.auth.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Service
public class TaskService {
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    private final TagRepository tagRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository, UserRepository userRepository, TagRepository tagRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
        this.tagRepository = tagRepository;
    }

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    @Transactional
    public Task createTaskForUser(long userId, String title, String content, List<String> tags) {
        User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User not found!!!"));
        Task task = new Task();
        task.setUser(user);
        task.setTitle(title);
        task.setContent(content);
        Date date = new Date();
        task.setDate(date);
        taskRepository.save(task);
        List<Tag> newTags = new LinkedList<>();
        for (String tagName : tags) {
            Tag existingTag = tagRepository.findTagByTitle(tagName);
            if (existingTag == null || existingTag.getTitle().isEmpty()) {
                Tag newTag = new Tag();
                newTag.setTitle(tagName);
                List<Task> tasks = new LinkedList<>();
                tasks.add(task);
                newTag.setTasks(tasks);
                tagRepository.save(newTag);
                newTags.add(newTag);
            } else {
                List<Task> tasks = existingTag.getTasks();
                tasks.add(task);
                existingTag.setTasks(tasks);
                tagRepository.save(existingTag);
                newTags.add(existingTag);
            }
        }
        task.setTags(newTags);
        return taskRepository.save(task);
    }

    public List<Task> getTaskByUser(long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User not found!!!"));
        return user.getTasks();
    }

    public Task updateTask(long id, String title, String content) {
        Task existingTask = taskRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Task not found!!!!"));
        existingTask.setTitle(title);
        existingTask.setContent(content);
        return taskRepository.save(existingTask);
    }
}
