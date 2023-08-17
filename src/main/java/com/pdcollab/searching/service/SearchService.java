package com.pdcollab.searching.service;

import com.pdcollab.blogs.model.Blog;
import com.pdcollab.blogs.repository.BlogRepository;
import com.pdcollab.issues.model.Issue;
import com.pdcollab.issues.repository.IssueRepository;
import com.pdcollab.tasks.model.Task;
import com.pdcollab.tasks.repository.TaskRepository;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.List;


@Service
public class SearchService {

    private final BlogRepository blogRepository;
    private final IssueRepository issueRepository;
    private final TaskRepository taskRepository;

    public SearchService(TaskRepository taskRepository, BlogRepository blogRepository, IssueRepository issueRepository) {
        this.taskRepository = taskRepository;
        this.blogRepository = blogRepository;
        this.issueRepository = issueRepository;
    }

    public List<Blog> getBlogs(List<String> tagNames) {
        List<Blog> blogs = blogRepository.findBlogsByTagNames(tagNames);
        if (blogs != null) {
            return blogs;
        } else {
            throw new NotFoundException("Entity Not found");
        }
    }

    public List<Issue> getIssues(List<String> tagNames) {
        List<Issue> issues = issueRepository.findIssueByTagNames(tagNames);
        if (issues != null) {
            return issues;
        } else {
            throw new NotFoundException("Entity Not Found");
        }
    }

    public List<Task> getTasks(List<String> tagNames) {
        List<Task> tasks = taskRepository.findTasksByTagNames(tagNames);
        if (tasks != null) {
            return tasks;
        } else {
            throw new NotFoundException("Entity Not Found");
        }
    }
}
