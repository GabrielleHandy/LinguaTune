package com.example.linguatune.controller;

import com.example.linguatune.model.StudyPage;
import com.example.linguatune.model.User;
import com.example.linguatune.service.StudyPageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Optional;

@RestController
@RequestMapping(path = "api/studypages")
public class StudyPageController {

    @Autowired
    private StudyPageService studyPageService;

    static HashMap<String, Object> result = new HashMap<>();

    @PostMapping(path = "/create/French")
    public ResponseEntity<?> createFrenchStudyPage() {
        Optional<StudyPage> studyPageOptional = Optional.ofNullable(studyPageService.createStudyPage("French"));
        if (studyPageOptional.isPresent()) {
            result.put("message", "Created successfully!");
            result.put("data", studyPageOptional.get());
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            result.put("message", "StudyPage already exists");
            return new ResponseEntity<>(result, HttpStatus.CONFLICT);
        }
    }
}
