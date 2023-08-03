package com.pdcollab.blogs.controller;

import com.pdcollab.blogs.model.Blog;
import com.pdcollab.blogs.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/blogs")
public class BlogController {
    private final BlogService blogService;

    @Autowired
    public BlogController(BlogService blogService) {
        this.blogService = blogService;
    }

    @PostMapping
    public ResponseEntity<Blog> createBlog(@RequestBody Blog blog) {
        Blog createdBlog = blogService.createBlog(blog);
        return new ResponseEntity<>(createdBlog, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Blog> getBlogById(@PathVariable Long id) {
        try {
            Blog blog = blogService.getBlogById(id);
            return new ResponseEntity<>(blog, HttpStatus.OK);
        } catch (Exception E) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
