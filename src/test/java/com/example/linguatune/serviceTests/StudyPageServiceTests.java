package com.example.linguatune.serviceTests;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.linguatune.model.Language;
import com.example.linguatune.model.StudyPage;
import com.example.linguatune.model.User;
import com.example.linguatune.repository.LanguageRepository;
import com.example.linguatune.repository.StudyPageRepository;
import com.example.linguatune.repository.UserRepository;
import com.example.linguatune.security.MyUserDetails;
import com.example.linguatune.service.StudyPageService;
import com.example.linguatune.service.UserService;

@ExtendWith(MockitoExtension.class)
public class StudyPageServiceTests {

     @Mock
    UserService userService;



    @InjectMocks
    StudyPageService studyPageServiceMock;

   
   
    @Mock
    LanguageRepository languageRepository;

    @Mock
    StudyPageRepository studyPageRepository;

    private static User user;
    private Language eng;

      @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
        studyPageServiceMock = new StudyPageService(userService, studyPageRepository);
        



        eng = new Language();
        eng.setName("English");
        eng.setLanguageCode("en");
        eng.setId(1L);


        user = new User(1L, "LanguageLover", "test@test.com", eng, "111", new ArrayList<StudyPage>() );
        studyPageServiceMock.setTestLoggedInUser(user);
    }
}

    

