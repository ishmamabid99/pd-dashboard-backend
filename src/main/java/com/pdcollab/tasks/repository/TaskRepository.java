package com.pdcollab.tasks.repository;

import com.pdcollab.tasks.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    @Query("SELECT DISTINCT ts FROM Task ts JOIN ts.tags t WHERE t.title IN :tagNames")
    public List<Task> findTasksByTagNames(List<String> tagNames);
}
