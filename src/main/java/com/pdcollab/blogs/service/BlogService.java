package com.pdcollab.blogs.service;

import com.pdcollab.blogs.model.Blog;
import com.pdcollab.blogs.repository.BlogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
public class BlogService {
    private final BlogRepository blogRepository;

    @Autowired
    public BlogService(BlogRepository blogRepository) {
        this.blogRepository = blogRepository;
    }

    public List<Blog> getAllBlogs() {
        return blogRepository.findAll();
    }

    public Blog createBlog(Blog blog) {
        return blogRepository.save(blog);
    }

    public Blog getBlogById(Long id) {
        return blogRepository.findById(id).get();
    }

    public Blog updateBlog(Long id, Blog blog) {

        Blog existingBlog = blogRepository.findById(id).get();
        existingBlog.setContent(blog.getContent());
        return blogRepository.save(existingBlog);
    }
}
