package com.pdcollab.blogs.repository;

import com.pdcollab.blogs.model.Blog;
import com.pdcollab.issues.model.Issue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlogRepository extends JpaRepository<Blog, Long> {

}
