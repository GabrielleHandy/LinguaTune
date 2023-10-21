package com.example.linguatune.serviceTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
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

import com.example.linguatune.exceptions.InformationNotFoundException;
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
        studyPageServiceMock = new StudyPageService(userService, studyPageRepository, languageRepository);
        



        eng = new Language();
        eng.setName("English");
        eng.setLanguageCode("en");
        eng.setId(1L);


        user = new User(1L, "LanguageLover", "test@test.com", eng, "111", new ArrayList<StudyPage>() );
        studyPage = new StudyPage(1L, user, eng, null, null);
        studyPageServiceMock.setTestLoggedInUser(user);
    }


    @Test
    public void testfindStudyPageById(){
        when(studyPageRepository.findById(anyLong())).thenReturn(Optional.of(studyPage));
        assertEquals(studyPageServiceMock.findStudyPageById(1L).getId(), studyPage.getId());
    }

    @Test
    public void testfindStudyPageByIdFail(){

        when(studyPageRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(InformationNotFoundException.class, () -> {

            studyPageServiceMock.findStudyPageById(1L);

        });
    }

    @Test 
    public void testCreateStudyPage(){
    when(languageRepository.findByName("English")).thenReturn(eng);
    when(studyPageRepository.save(any(StudyPage.class))).thenReturn(studyPage);
    StudyPage result = studyPageServiceMock.createStudyPage("English");
    assertEquals(result.getUser().getEmailAddress(), user.getEmailAddress());

    }

}

    

