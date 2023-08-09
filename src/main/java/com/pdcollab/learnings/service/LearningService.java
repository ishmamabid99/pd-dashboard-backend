package com.pdcollab.learnings.service;

import com.pdcollab.learnings.model.Tag;
import com.pdcollab.learnings.model.UserTagMapping;
import com.pdcollab.learnings.repository.TagRepository;
import com.pdcollab.learnings.repository.UserTagMappingRepository;
import com.pdcollab.users.model.User;
import com.pdcollab.users.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class LearningService {
    private final TagRepository tagRepository;
    private final UserTagMappingRepository userTagMappingRepository;

    private final UserRepository userRepository;

    @Autowired
    public LearningService(TagRepository tagRepository, UserTagMappingRepository userTagMappingRepository, UserRepository userRepository) {
        this.tagRepository = tagRepository;
        this.userTagMappingRepository = userTagMappingRepository;
        this.userRepository = userRepository;
    }

    public List<Tag> getAllTags() {
        return tagRepository.findAll();
    }


    public List<UserTagMapping> getUsersTag(long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User not found!!!"));
        List<UserTagMapping> userTagMappingList = userTagMappingRepository.findByUser(user);
        List<UserTagMapping> newUserTagMappings = new LinkedList<>(userTagMappingList);
        List<Tag> tags = tagRepository.findAll();
        for (Tag tag : tags) {
            boolean isAssociated = userTagMappingList.stream().anyMatch(mapping -> mapping.getTag().equals(tag));
            if (!isAssociated) {
                UserTagMapping tempTagMapping = new UserTagMapping();
                tempTagMapping.setTag(tag);
                tempTagMapping.setTagName(tag.getTitle());
                tempTagMapping.setUser(user);
                tempTagMapping.setProficiency("");
                newUserTagMappings.add(tempTagMapping);
            }
        }
        return newUserTagMappings;
    }

    @Transactional
    public UserTagMapping createNewTag(long userId, Tag tag, String proficiency) {
        User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User not found!!!"));
        Tag existingTag = tagRepository.findByTitle(tag.getTitle());
        if (existingTag == null) {
            Tag newTag = tagRepository.save(tag);
            UserTagMapping userTagMapping = new UserTagMapping();
            userTagMapping.setTag(newTag);
            userTagMapping.setUser(user);
            userTagMapping.setTagName(tag.getTitle());
            userTagMapping.setProficiency(proficiency);
            return userTagMappingRepository.save(userTagMapping);
        } else {
            UserTagMapping userExistingTag = userTagMappingRepository.findByUserAndTag(user, tag);
            if (userExistingTag == null) {
                UserTagMapping newUserTagMapping = new UserTagMapping();
                newUserTagMapping.setUser(user);
                newUserTagMapping.setTag(tag);
                newUserTagMapping.setProficiency(proficiency);
                newUserTagMapping.setTagName(tag.getTitle());
                return userTagMappingRepository.save(newUserTagMapping);
            } else {
                userExistingTag.setProficiency(proficiency);
                return userTagMappingRepository.save(userExistingTag);
            }
        }
    }
}
