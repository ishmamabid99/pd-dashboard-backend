package com.pdcollab.blogs.controller;

import com.pdcollab.blogs.model.Blog;
import com.pdcollab.blogs.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/blogs")
public class BlogController {
    private final BlogService blogService;

    @Autowired
    public BlogController(BlogService blogService) {
        this.blogService = blogService;
    }

    @GetMapping
    public ResponseEntity<List<Blog>> getAllBlogs() {
        List<Blog> allBlogs = blogService.getAllBlogs();
        return new ResponseEntity<>(allBlogs, HttpStatus.OK);
    }

    @PostMapping("/{userId}/users")
    public ResponseEntity<Blog> createBlog(@PathVariable long userId, @RequestBody Blog blog) {
        try {
            Blog createdBlog = blogService.createBlogForUser(userId, blog);
            return new ResponseEntity<>(createdBlog, HttpStatus.CREATED);
        } catch (Exception E) {
            System.out.println(E);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{userId}/users")
    public ResponseEntity<List<Blog>> getBlogByUsers(@PathVariable Long userId) {
        try {
            List<Blog> blogs = blogService.getBlogByUser(userId);
            return new ResponseEntity<>(blogs, HttpStatus.OK);
        } catch (Exception E) {
            System.out.println(E);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
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

    @PutMapping("/{id}")
    public ResponseEntity<Blog> updateBlog(@PathVariable Long id, @RequestBody Blog blog) {
        try {
            Blog updatedBlog = blogService.updateBlog(id, blog);
            return new ResponseEntity<>(updatedBlog, HttpStatus.OK);
        } catch (Exception E) {
            System.out.println(E);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
