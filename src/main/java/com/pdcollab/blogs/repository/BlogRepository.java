package com.pdcollab.blogs.repository;

import com.pdcollab.blogs.model.Blog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BlogRepository extends JpaRepository<Blog, Long> {
    @Query("SELECT DISTINCT b FROM Blog b JOIN b.tags t WHERE t.title IN :tagNames")
    public List<Blog> findBlogsByTagNames(List<String> tagNames);
}
