package com.pdcollab.searching.controller;

import com.pdcollab.blogs.model.Blog;
import com.pdcollab.issues.model.Issue;
import com.pdcollab.searching.service.SearchService;
import com.pdcollab.tasks.model.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/api/search")
public class SearchController {
    private final SearchService searchService;

    @Autowired
    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }

    @GetMapping("/blogs/")
    public ResponseEntity<List<Blog>> SearchBlogs(@RequestParam("tags") List<String> tagNames) {
        try {
            List<Blog> blogs = searchService.getBlogs(tagNames);
            return new ResponseEntity<>(blogs, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/issues/")
    public ResponseEntity<List<Issue>> SearchIssues(@RequestParam List<String> tagNames) {
        try {
            List<Issue> issues = searchService.getIssues(tagNames);
            return new ResponseEntity<>(issues, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/tasks/")
    public ResponseEntity<List<Task>> searchTasks(@RequestParam List<String> tagNames) {
        try {
            List<Task> tasks = searchService.getTasks(tagNames);
            return new ResponseEntity<>(tasks, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
