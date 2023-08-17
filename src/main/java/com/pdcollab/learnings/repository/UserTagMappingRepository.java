package com.pdcollab.learnings.repository;

import com.pdcollab.learnings.model.UserTagMapping;
import com.pdcollab.users.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserTagMappingRepository extends JpaRepository<UserTagMapping, Long> {

    public UserTagMapping findUserTagMappingByUserAndTagName(User user, String tagName);
}
