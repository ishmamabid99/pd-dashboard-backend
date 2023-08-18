package com.pdcollab.comments.repository;

import com.pdcollab.comments.model.IssueComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IssueCommentRepository extends JpaRepository<IssueComment, Long> {
    public IssueComment findIssueCommentByIsPinnedTrue();
}
