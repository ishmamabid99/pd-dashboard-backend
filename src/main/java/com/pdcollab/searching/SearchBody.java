package com.pdcollab.searching;

import com.pdcollab.blogs.model.Blog;
import com.pdcollab.issues.model.Issue;
import com.pdcollab.tasks.model.Task;

import java.util.List;

public class SearchBody {

    private List<Blog> blogs;
    private List<Issue> issues;

    private List<Task> tasks;

    public List<Blog> getBlogs() {
        return blogs;
    }

    public void setBlogs(List<Blog> blogs) {
        this.blogs = blogs;
    }

    public List<Issue> getIssues() {
        return issues;
    }

    public void setIssues(List<Issue> issues) {
        this.issues = issues;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }
}
