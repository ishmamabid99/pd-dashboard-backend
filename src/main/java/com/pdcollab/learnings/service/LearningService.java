package com.pdcollab.learnings.service;

import com.pdcollab.learnings.model.Tag;
import com.pdcollab.learnings.model.UserTagMapping;
import com.pdcollab.learnings.repository.TagRepository;
import com.pdcollab.learnings.repository.UserTagMappingRepository;
import com.pdcollab.learnings.utils.TagBody;
import com.pdcollab.users.model.User;
import com.pdcollab.users.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @Transactional
    public List<UserTagMapping> createUserTagMapping(long userId, List<TagBody> tags) {
        User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User not found!!!"));
        for (TagBody tag : tags) {
            String tagName = tag.getTagName();
            UserTagMapping exisUserTagMapping = userTagMappingRepository.findUserTagMappingByUserAndTagName(user, tagName);
            Tag existTag = tagRepository.findTagByTitle(tagName);
            if (exisUserTagMapping == null) {
                UserTagMapping newUserTagMapping = new UserTagMapping();
                newUserTagMapping.setTagname(tagName);
                newUserTagMapping.setProficiency(tag.getProficiency());
                newUserTagMapping.setUser(user);
                if (existTag == null) {
                    Tag newTag = new Tag();
                    newTag.setTitle(tagName);
                    tagRepository.save(newTag);
                }
                userTagMappingRepository.save(newUserTagMapping);
            } else {
                exisUserTagMapping.setProficiency(tag.getProficiency());
                userTagMappingRepository.save(exisUserTagMapping);
            }
        }
        return userRepository.findById(userId).get().getUserTagMappings();
    }

    public List<UserTagMapping> getAllUserTagMappings() {
        return userTagMappingRepository.findAll();
    }

    public List<Tag> getTags() {
        return tagRepository.findAll();
    }

    public List<UserTagMapping> getUserTagMappingsWithDefault(long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User not found!!!"));
        List<UserTagMapping> existingTags = user.getUserTagMappings();
        List<Tag> tags = this.getTags();
        for (Tag tag : tags) {
            boolean isAssociated = existingTags.stream().anyMatch(mapping -> mapping.getTagname().equals(tag.getTitle()));
            if (!isAssociated) {
                UserTagMapping newTagMapping = new UserTagMapping();
                newTagMapping.setProficiency("");
                newTagMapping.setTagname(tag.getTitle());
                existingTags.add(newTagMapping);
            }
        }
        return existingTags;
    }
}
