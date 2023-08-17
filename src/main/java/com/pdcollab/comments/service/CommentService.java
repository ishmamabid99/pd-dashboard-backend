package com.pdcollab.comments.service;

import com.pdcollab.blogs.model.Blog;
import com.pdcollab.blogs.repository.BlogRepository;
import com.pdcollab.comments.model.BlogComment;
import com.pdcollab.comments.model.IssueComment;
import com.pdcollab.comments.repository.BlogCommentRepository;
import com.pdcollab.comments.repository.IssueCommentRepository;
import com.pdcollab.issues.model.Issue;
import com.pdcollab.issues.repository.IssueRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;


@Service
public class CommentService {
    private final BlogCommentRepository blogCommentRepository;
    private final IssueCommentRepository issueCommentRepository;

    private final IssueRepository issueRepository;
    private final BlogRepository blogRepository;

    @Autowired
    public CommentService(BlogCommentRepository blogCommentRepository, IssueCommentRepository issueCommentRepository, IssueRepository issueRepository, BlogRepository blogRepository) {
        this.blogCommentRepository = blogCommentRepository;
        this.issueCommentRepository = issueCommentRepository;
        this.issueRepository = issueRepository;
        this.blogRepository = blogRepository;

    }

    public IssueComment saveIssueComment(long issueId, IssueComment issueComment) {
        Issue issue = issueRepository.findById(issueId).orElseThrow(() -> new EntityNotFoundException("Issue not found!!!"));
        issueComment.setCreatedAt(LocalDateTime.now());
        issueComment.setIssue(issue);
        return issueCommentRepository.save(issueComment);
    }


    public BlogComment saveBlogComment(long blogId, BlogComment blogComment) {
        Blog blog = blogRepository.findById(blogId).orElseThrow(() -> new EntityNotFoundException("Blog not found!!!"));
        blogComment.setCreatedAt(LocalDateTime.now());
        blogComment.setBlog(blog);
        return blogCommentRepository.save(blogComment);
    }

    public BlogComment pinBlogComment(long commentId) {
        BlogComment existingBlogComment = blogCommentRepository.findBlogCommentByIsPinnedTrue();
        if (existingBlogComment != null) {
            existingBlogComment.setPinned(false);
            blogCommentRepository.save(existingBlogComment);
        }
        BlogComment blogComment = blogCommentRepository.findById(commentId).orElseThrow(() -> new EntityNotFoundException("Blog comment not found!!!"));
        blogComment.setPinned(true);
        return blogCommentRepository.save(blogComment);

    }
}
