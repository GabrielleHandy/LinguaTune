package com.example.linguatune.service;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.linguatune.exceptions.AlreadyExistException;
import com.example.linguatune.exceptions.InformationNotFoundException;
import com.example.linguatune.model.Language;
import com.example.linguatune.model.StudyPage;
import com.example.linguatune.model.User;
import com.example.linguatune.repository.LanguageRepository;
import com.example.linguatune.repository.StudyPageRepository;
import com.example.linguatune.security.MyUserDetails;

@Service
public class StudyPageService {

    private final StudyPageRepository studyPageRepository;
    
    private LanguageRepository languageRepository;

    private final UserService userService;

    private static User loggedinUser;

    @Autowired
    public StudyPageService(UserService userService, StudyPageRepository studyPageRepository, LanguageRepository languageRepository){
        this.userService = userService;
        this.studyPageRepository = studyPageRepository;
        this.languageRepository = languageRepository;
    }

    public void setTestLoggedInUser(User user){
        loggedinUser = user;

    }

    public void setLoggedInUser() {
        MyUserDetails userDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        loggedinUser = userDetails.getUser();
        
    }

    public StudyPage findStudyPageById(long id){
        Optional<StudyPage> optionalStudyPage = studyPageRepository.findById(id);
        if(optionalStudyPage.isPresent()){
            return optionalStudyPage.get();

        }
        throw new InformationNotFoundException("StudyPage with id " + id + " doesn't exist");
    }

    public StudyPage createStudyPage(String language){
        
            Language language1 = languageRepository.findByName(language);
            if(!loggedinUser.getStudyPages().stream().anyMatch(studyPage -> studyPage.getLanguage().getName() == language1.getName())){
            StudyPage newStudyPage = new StudyPage();
            newStudyPage.setLanguage(language1);
            newStudyPage.setUser(loggedinUser);
            return studyPageRepository.save(newStudyPage); 
        }
        throw new AlreadyExistException("You already have a Study Page for " + language);
    
        
        }
        

}
