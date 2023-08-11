package com.pdcollab.learnings.repository;

import com.pdcollab.learnings.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {
    public Tag findTagByTitle(String title);
}
