package com.pdcollab.blogs.repository;

import com.pdcollab.blogs.model.BlogImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlogImageRepository extends JpaRepository<BlogImage, Long> {
}
