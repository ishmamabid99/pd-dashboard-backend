package com.pdcollab.issues.controller;

import com.pdcollab.issues.model.Issue;
import com.pdcollab.issues.service.IssueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/issues")
public class IssueController {
    private final IssueService issueService;

    @Autowired
    public IssueController(IssueService issueService) {
        this.issueService = issueService;
    }

    @GetMapping
    public ResponseEntity<List<Issue>> getAllIssues() {
        List<Issue> issues = issueService.getAllIssues();
        return new ResponseEntity<>(issues, HttpStatus.OK);
    }

    @PostMapping("/{userId}/users")
    public ResponseEntity<Issue> createIssueForUser(@PathVariable Long userId, @RequestParam String title, @RequestParam String content, @RequestParam List<String> tags, @RequestParam("images") MultipartFile[] files) {
        try {
            Issue createdIssue = issueService.createIssueForUser(userId, title, content, tags, files);
            return new ResponseEntity<>(createdIssue, HttpStatus.CREATED);
        } catch (Exception E) {
            System.out.println(E);
            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        }
    }

    @GetMapping("/images/{filename}")
    public ResponseEntity<UrlResource> serveFile(@PathVariable String filename) {
        try {
            return new ResponseEntity<>(issueService.getImageResource(filename), HttpStatus.OK);
        } catch (Exception E) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{userId}/users")
    public ResponseEntity<List<Issue>> getIssuesByUser(@PathVariable long userId) {
        try {
            List<Issue> issues = issueService.getIssueByUser(userId);
            return new ResponseEntity<>(issues, HttpStatus.OK);
        } catch (Exception E) {
            System.out.println(E);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Issue> updateIssue(@PathVariable long id, @RequestParam String title, @RequestParam String content) {
        try {
            Issue updatedIssue = issueService.updateIssue(id, title, content);
            return new ResponseEntity<>(updatedIssue, HttpStatus.OK);
        } catch (Exception E) {
            System.out.println(E);
            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        }
    }
}
