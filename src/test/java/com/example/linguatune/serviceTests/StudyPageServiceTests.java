package com.example.linguatune.serviceTests;

import com.example.linguatune.exceptions.AlreadyExistException;
import com.example.linguatune.exceptions.InformationNotFoundException;
import com.example.linguatune.model.Language;
import com.example.linguatune.model.StudyPage;
import com.example.linguatune.model.User;
import com.example.linguatune.repository.LanguageRepository;
import com.example.linguatune.repository.StudyPageRepository;
import com.example.linguatune.repository.UserRepository;
import com.example.linguatune.service.StudyPageService;
import com.example.linguatune.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class StudyPageServiceTests {

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @InjectMocks
    StudyPageService studyPageServiceMock;


    @Mock
    LanguageRepository languageRepository;

    @Mock
    StudyPageRepository studyPageRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    private static User user;
    private Language eng;
    private Language fre;
    private StudyPage studyPage;
    private List<StudyPage> pages;



    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        studyPageServiceMock = new StudyPageService(userService, studyPageRepository, languageRepository);


        eng = new Language();
        eng.setName("English");
        eng.setLanguageCode("en");
        eng.setId(1L);

        fre = new Language();
        fre.setId(2L);
        fre.setName("French");


        pages = new ArrayList<StudyPage>();

        user = new User(1L, "LanguageLover", "test@test.com",  "111", new ArrayList<StudyPage>());

        studyPage = new StudyPage(1L, user, fre, null, null);
        user.setStudyPages(pages);
        pages.add(studyPage);

        loginUser();
    }


    @Test
    public void testfindStudyPageById() {
        when(studyPageRepository.findById(anyLong())).thenReturn(Optional.of(studyPage));
        assertEquals(studyPageServiceMock.findStudyPageById(1L).getId(), studyPage.getId());
    }

    @Test
    public void testfindStudyPageByIdFail() {

        when(studyPageRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(InformationNotFoundException.class, () -> {

            studyPageServiceMock.findStudyPageById(1L);

        });
    }

    @Test
    public void testCreateStudyPage() {
        when(languageRepository.findByName("English")).thenReturn(eng);
        when(studyPageRepository.save(any(StudyPage.class))).thenReturn(studyPage);
        StudyPage result = studyPageServiceMock.createStudyPage("English");
        assertEquals(result.getUser().getEmailAddress(), user.getEmailAddress());

    }

    @Test
    public void testCreateStudyPageFail() {


        when(languageRepository.findByName("French")).thenReturn(fre);

        assertThrows(AlreadyExistException.class, () -> {

            studyPageServiceMock.createStudyPage("French");

        });

    }

    @Test
    public void testDeleteStudyPage() {
        when(studyPageRepository.findByIdAndUser(anyLong(), any(User.class))).thenReturn(studyPage);
        StudyPage result = studyPageServiceMock.deleteStudyPage(1L);
        assert(result.getLanguage().getName().equals(fre.getName()));

    }


    @Test
    public void testDeleteStudyPageFail() {

        when(studyPageRepository.findByIdAndUser(anyLong(), any(User.class))).thenReturn(null);

        assertThrows(InformationNotFoundException.class, () -> {

            studyPageServiceMock.deleteStudyPage(1L);

        });
    }

    private void loginUser() {


        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken("test@test", "1111");

        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

    }

}
    

