package com.pdcollab.searching.service;

import com.pdcollab.blogs.repository.BlogRepository;
import com.pdcollab.issues.repository.IssueRepository;
import com.pdcollab.tasks.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SearchService {
    private final IssueRepository issueRepository;
    private final BlogRepository blogRepository;

    private final TaskRepository taskRepository;

    @Autowired
    public SearchService(IssueRepository issueRepository, BlogRepository blogRepository, TaskRepository taskRepository) {
        this.blogRepository = blogRepository;
        this.issueRepository = issueRepository;
        this.taskRepository = taskRepository;
    }

}
