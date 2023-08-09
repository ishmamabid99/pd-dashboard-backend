package com.pdcollab.learnings.repository;

import com.pdcollab.learnings.model.Tag;
import com.pdcollab.learnings.model.UserTagMapping;
import com.pdcollab.users.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserTagMappingRepository extends JpaRepository<UserTagMapping,  Long> {
    public List<UserTagMapping> findByUser(User user);
    public  UserTagMapping findByUserAndTag(User user, Tag tag);
}
