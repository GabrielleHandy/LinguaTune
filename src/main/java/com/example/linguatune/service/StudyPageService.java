package com.example.linguatune.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.linguatune.repository.LanguageRepository;
import com.example.linguatune.repository.StudyPageRepository;

@Service
public class StudyPageService {

    private final StudyPageRepository studyPageRepository;
    @Autowired
    private LanguageRepository languageRepository;

    private final UserService userService;

    @Autowired
    public StudyPageService(UserService userService, StudyPageRepository studyPageRepository){
        this.userService = userService;
        this.studyPageRepository = studyPageRepository;
    }
}
