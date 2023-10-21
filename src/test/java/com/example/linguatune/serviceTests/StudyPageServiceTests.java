package com.example.linguatune.serviceTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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
    private StudyPage studyPage;
      @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
        studyPageServiceMock = new StudyPageService(userService, studyPageRepository);
        



        eng = new Language();
        eng.setName("English");
        eng.setLanguageCode("en");
        eng.setId(1L);


        user = new User(1L, "LanguageLover", "test@test.com", eng, "111", new ArrayList<StudyPage>() );
        studyPage = new StudyPage(1L, user, eng, null, null);
        studyPageServiceMock.setTestLoggedInUser(user);
    }


     @Test
    public void testGetUserById(){
        when(studyPageRepository.findById(anyLong())).thenReturn(Optional.of(studyPage));
        assertEquals(studyPageServiceMock.findById(1L).getId(), user.getId());
    }

}

    

