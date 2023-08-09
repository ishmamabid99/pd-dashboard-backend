package com.pdcollab.learnings.controller;

import com.pdcollab.learnings.model.Tag;
import com.pdcollab.learnings.model.UserTagMapping;
import com.pdcollab.learnings.service.LearningService;
import com.pdcollab.learnings.utils.TagCreationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/learnings")
public class LearningController {
    private final LearningService learningService;

    @Autowired
    public LearningController(LearningService learningService) {
        this.learningService = learningService;
    }

    @GetMapping
    public ResponseEntity<List<Tag>> getAllTags() {
        return new ResponseEntity<>(learningService.getAllTags(), HttpStatus.OK);
    }

    @GetMapping("/{userId}/users")
    public ResponseEntity<List<UserTagMapping>> getUsersTags(@PathVariable long userId) {
        try {
            List<UserTagMapping> userTagMappingList = learningService.getUsersTag(userId);
            return new ResponseEntity<>(userTagMappingList, HttpStatus.OK);
        } catch (Exception E) {
            System.out.println(E);
            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        }
    }

    @PostMapping("/{userId}/users")
    public ResponseEntity<UserTagMapping> createNewTag(@PathVariable long userId, @RequestBody TagCreationRequest tagCreationRequest) {
        try {
            Tag tag = tagCreationRequest.getTag();
            String proficiency = tagCreationRequest.getProficiency();
            UserTagMapping createdUserTagMapping = learningService.createNewTag(userId, tag, proficiency);
            return new ResponseEntity<>(createdUserTagMapping, HttpStatus.CREATED);
        } catch (Exception E) {

//            System.out.println(E);
            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        }
    }
}
