package com.example.linguatune.controller;

import com.example.linguatune.model.User;
import com.example.linguatune.service.StudyPageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping(path = "api/studypages")
public class StudyPageController {

    @Autowired
    private StudyPageService studyPageService;

//    @PostMapping(path = "/create/French")
//    public ResponseEntity<?> createFrenchStudyPage(){
//        Optional<User> userOptional = Optional.ofNullable(userService.createUser(user));
//        if(userOptional.isPresent()){
//            result.put("message", "Created successfully!");
//            result.put("data", userOptional.get());
//            return new ResponseEntity<>(result, HttpStatus.OK);
//        }else {
//            result.put("message", "User already exists");
//            return new ResponseEntity<>(result, HttpStatus.CONFLICT);
//        }
}
