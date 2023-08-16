package com.pdcollab.issues.repository;

import com.pdcollab.issues.model.IssueImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IssueImageRepository extends JpaRepository<IssueImage, Long> {
}
