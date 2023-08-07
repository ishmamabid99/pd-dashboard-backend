package com.pdcollab.issues.repository;

import com.pdcollab.issues.model.Issue;
import com.pdcollab.users.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IssueRepository extends JpaRepository<Issue, Long> {
    List<Issue> findIssueByUser(User user);
}
