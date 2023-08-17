package com.pdcollab.blogs.service;

import com.pdcollab.blogs.model.Blog;
import com.pdcollab.blogs.model.BlogImage;
import com.pdcollab.blogs.repository.BlogImageRepository;
import com.pdcollab.blogs.repository.BlogRepository;
import com.pdcollab.learnings.model.Tag;
import com.pdcollab.learnings.repository.TagRepository;
import com.pdcollab.users.model.User;
import com.pdcollab.users.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Service
public class BlogService {
    @Value("${upload.base-dir}")
    private String baseUploadDir;
    private final BlogRepository blogRepository;
    private final UserRepository userRepository;

    private final BlogImageRepository blogImageRepository;

    private final TagRepository tagRepository;

    @Autowired
    public BlogService(BlogRepository blogRepository, UserRepository userRepository, BlogImageRepository blogImageRepository, TagRepository tagRepository) {
        this.blogRepository = blogRepository;
        this.userRepository = userRepository;
        this.blogImageRepository = blogImageRepository;
        this.tagRepository = tagRepository;
    }

    public List<Blog> getAllBlogs() {
        return blogRepository.findAll();
    }

    @Transactional
    public Blog createBlogForUser(Long userId, String title, String content, List<String> tags, MultipartFile[] imageFiles) throws IOException {
        User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User Not Found!!!"));
        Blog blog = new Blog();
        blog.setTitle(title);
        blog.setContent(content);
        blog.setUser(user);
        Date date = new Date();
        blog.setDate(date);
        if (imageFiles != null) {
            List<BlogImage> blogImages = new LinkedList<>();
            for (MultipartFile imageFile : imageFiles) {
                BlogImage blogImage = new BlogImage();
                blogImage.setBlog(blog);
                blogImage.setFilename(imageFile.getOriginalFilename());
                File directory = new File(baseUploadDir);
                if (!directory.exists()) {
                    directory.mkdirs();
                }
                String filePath = baseUploadDir + "/" + imageFile.getOriginalFilename();
                Files.copy(imageFile.getInputStream(), Paths.get(filePath), StandardCopyOption.REPLACE_EXISTING);
                blogImages.add(blogImage);
                blogImageRepository.save(blogImage);
            }
            blog.setImages(blogImages);
        }
        blogRepository.save(blog);
        System.out.println(blog.getId());
        List<Tag> newTags = new LinkedList<>();
        for (String tagName : tags) {
            System.out.println(tagName);
            Tag existingTag = tagRepository.findTagByTitle(tagName);
            if (existingTag == null || existingTag.getTitle().isEmpty()) {
                Tag newtag = new Tag();
                newtag.setTitle(tagName);
                List<Blog> blogs = new LinkedList<>();
                blogs.add(blog);
                newtag.setBlogs(blogs);
                tagRepository.save(newtag);
                newTags.add(newtag);
            } else {
                List<Blog> blogs = existingTag.getBlogs();
                blogs.add(blog);
                tagRepository.save(existingTag);
                newTags.add(existingTag);
            }
        }
        blog.setTags(newTags);
        return blogRepository.save(blog);

    }

    public UrlResource getImageResource(String filename) throws IOException {
        Path file = Paths.get(baseUploadDir).resolve(filename);
        UrlResource resource = new UrlResource(file.toUri());
        if (resource.exists() && resource.isReadable()) {
            return resource;
        } else {
            return null;
        }
    }

    public Blog getBlogById(Long id) {
        return blogRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Blog not found!!!"));
    }

    public List<Blog> getBlogByUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User Not Found!!!"));
        return user.getBlogs();
    }

    public Blog updateBlog(Long id, String title, String content) {
        Blog existingBlog = blogRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Blog not found!!!"));
        existingBlog.setTitle(title);
        existingBlog.setContent(content);
        return blogRepository.save(existingBlog);
    }
}
