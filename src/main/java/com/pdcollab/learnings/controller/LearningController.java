package com.pdcollab.learnings.controller;

import com.pdcollab.learnings.model.UserTagMapping;
import com.pdcollab.learnings.service.LearningService;
import com.pdcollab.learnings.utils.TagBody;
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

    @PostMapping("/{userId}")
    public ResponseEntity<List<UserTagMapping>> createUserMapping(@PathVariable long userId, @RequestBody List<TagBody> tags) {
        try {
            List<UserTagMapping> userTagMappingList = learningService.createUserTagMapping(userId, tags);
            return new ResponseEntity<>(userTagMappingList, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e);
            return new ResponseEntity<>(HttpStatus.METHOD_FAILURE);
        }
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<UserTagMapping>> getUserTagMappings(@PathVariable long userId) {
        return new ResponseEntity<>(learningService.getUserTagMappingsWithDefault(userId), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<UserTagMapping>> getAllTagMappings() {

        return new ResponseEntity<>(learningService.getAllUserTagMappings(), HttpStatus.OK);
    }
}
