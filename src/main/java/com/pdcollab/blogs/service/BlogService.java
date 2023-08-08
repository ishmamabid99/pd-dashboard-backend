package com.pdcollab.blogs.service;

import com.pdcollab.blogs.model.Blog;
import com.pdcollab.blogs.repository.BlogRepository;
import com.pdcollab.users.model.User;
import com.pdcollab.users.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
public class BlogService {
    private final BlogRepository blogRepository;
    private final UserRepository userRepository;

    @Autowired
    public BlogService(BlogRepository blogRepository, UserRepository userRepository) {
        this.blogRepository = blogRepository;
        this.userRepository = userRepository;

    }

    public List<Blog> getAllBlogs() {
        return blogRepository.findAll();
    }

    @Transactional
    public Blog createBlogForUser(Long userId, Blog blog) {
        User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User Not Found!!!"));
        blog.setUser(user);
        return blogRepository.save(blog);
    }

    public Blog getBlogById(Long id) {
        return blogRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Blog not found!!!"));
    }

    public List<Blog> getBlogByUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User Not Found!!!"));
        return user.getBlogs();
    }

    public Blog updateBlog(Long id, Blog blog) {
        Blog existingBlog = blogRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Blog not found!!!"));
        existingBlog.setContent(blog.getContent());
        existingBlog.setTitle(blog.getTitle());
        return blogRepository.save(existingBlog);
    }
}
