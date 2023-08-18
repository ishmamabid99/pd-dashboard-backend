package com.pdcollab.comments.controller;


import com.pdcollab.comments.model.BlogComment;
import com.pdcollab.comments.model.IssueComment;
import com.pdcollab.comments.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/comments")
public class CommentController {
    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/{issueId}/issue")
    public ResponseEntity<IssueComment> saveIssueComment(@PathVariable long issueId, @RequestBody IssueComment issueComment) {
        try {

            IssueComment savedIssueComment = commentService.saveIssueComment(issueId, issueComment);
            return new ResponseEntity<>(savedIssueComment, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/{blogId}/blog")
    public ResponseEntity<BlogComment> saveBlogComment(@PathVariable long blogId, @RequestBody BlogComment blogComment) {
        try {
            BlogComment savedBlogComment = commentService.saveBlogComment(blogId, blogComment);
            return new ResponseEntity<>(savedBlogComment, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/blog/{commentId}/pin")
    public ResponseEntity<BlogComment> savePinnedBlogComment(@PathVariable long commentId) {
        try {
            BlogComment savedBlogComment = commentService.pinBlogComment(commentId);
            return new ResponseEntity<>(savedBlogComment, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/issue/{commentId}/pin")
    public ResponseEntity<IssueComment> savePinnedIssueComment(@PathVariable long commentId) {
        try {
            IssueComment savedIssueComment = commentService.pinIssueComment(commentId);
            return new ResponseEntity<>(savedIssueComment, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
