package com.pdcollab.issues.service;

import com.pdcollab.issues.model.Issue;
import com.pdcollab.issues.repository.IssueRepository;
import com.pdcollab.users.model.User;
import com.pdcollab.users.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IssueService {

    private final IssueRepository issueRepository;
    private final UserRepository userRepository;

    @Autowired
    public IssueService(IssueRepository issueRepository, UserRepository userRepository) {
        this.issueRepository = issueRepository;
        this.userRepository = userRepository;
    }


    public List<Issue> getAllIssues() {
        return issueRepository.findAll();
    }

    @Transactional
    public Issue createIssueForUser(long userId, Issue issue) {
        User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User not found!!!"));
        issue.setUser(user);
        return issueRepository.save(issue);
    }

    public List<Issue> getIssueByUser(long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User not found!!!"));
        return user.getIssues();
    }

    public Issue updateIssue(long id, Issue issue) {
        Issue existingIssue = issueRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Issue not found"));
        existingIssue.setTitle(issue.getTitle());
        existingIssue.setContent(issue.getContent());
        return issueRepository.save(existingIssue);
    }

}
