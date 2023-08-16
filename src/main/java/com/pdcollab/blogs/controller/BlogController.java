package com.pdcollab.blogs.controller;

import com.pdcollab.blogs.model.Blog;
import com.pdcollab.blogs.service.BlogService;
import com.pdcollab.blogs.utils.BlogRequestBody;
import com.pdcollab.learnings.model.Tag;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.logging.Logger;

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

    @PostMapping(value = "/{userId}/users", consumes = {
            "multipart/form-data"
    }, headers = "Content-Type= multipart/form-data")
    public ResponseEntity<Blog> createBlog(@PathVariable long userId, @RequestParam String title, @RequestParam String content, @RequestParam List<Tag> tags, @RequestParam("images") MultipartFile[] files) {
        System.out.println(files.length);
        System.out.println(title + " " + content);
//        return new ResponseEntity<>(HttpStatus.OK);
        try {
            Blog createdBlog = blogService.createBlogForUser(userId, title, content, tags, files);
            return new ResponseEntity<>(createdBlog, HttpStatus.CREATED);
        } catch (Throwable E) {
            throw new MultipartException("Could not access multipart servlet request", E);
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/images/{fileName}")
    public ResponseEntity<UrlResource> serveFile(@PathVariable String fileName) {
        try {
            return new ResponseEntity<>(blogService.getImageResource(fileName), HttpStatus.OK);
        } catch (Exception E) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
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
