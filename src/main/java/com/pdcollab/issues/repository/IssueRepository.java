package com.pdcollab.issues.repository;

import com.pdcollab.issues.model.Issue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IssueRepository extends JpaRepository<Issue, Long> {
    @Query("SELECT DISTINCT i FROM Issue i JOIN i.tags t WHERE t.title IN :tagNames")
    public List<Issue> findIssueByTagNames(List<String> tagNames);
}
