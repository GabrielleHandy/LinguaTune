package com.example.linguatune.serviceTests;

import com.example.linguatune.exceptions.InformationNotFoundException;
import com.example.linguatune.model.FlashCardStack;
import com.example.linguatune.model.Language;
import com.example.linguatune.model.StudyPage;
import com.example.linguatune.model.User;
import com.example.linguatune.repository.FlashCardStackRepository;
import com.example.linguatune.repository.StudyPageRepository;
import com.example.linguatune.security.MyUserDetails;
import com.example.linguatune.service.FlashCardStackService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class FlashCardStackServiceTests {


    @Mock
    FlashCardStackRepository flashCardStackRepository;

    @Mock
    StudyPageRepository studyPageRepository;

    @InjectMocks
    FlashCardStackService flashCardStackService;


    @Autowired
    AuthenticationManager authenticationManager;




    private static User user;
    private Language eng;
    private Language spa;
    private StudyPage studyPage;
    private FlashCardStack updated;
    private FlashCardStack flashCardStack;

    @BeforeEach
    public void setUp(){




        eng = new Language();
        eng.setName("English");
        eng.setLanguageCode("en");
        eng.setId(1L);


        spa = new Language();
        spa.setId(2L);
        spa.setName("Spanish");
        user = new User(1L, "LanguageLover", "test@test.com", "111", new ArrayList<StudyPage>() );
        studyPage = new StudyPage(1L, user, spa, null, null);
        flashCardStack = new FlashCardStack(2L, "pop", studyPage, new ArrayList<>());


        updated = new FlashCardStack();
        updated.setTitle("Bloop");
    loginUser();


    }

    @Test
    public void testGetFlashCardStackById(){
        when(flashCardStackRepository.findById(anyLong())).thenReturn(Optional.ofNullable(flashCardStack));
        assertEquals(flashCardStackService.findById(1L).getId(), flashCardStack.getId());
    }

    @Test
    public void testGetUserByIdFail(){

        when(flashCardStackRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(InformationNotFoundException.class, () -> {

            flashCardStackService.findById(2L);

        });
    }

    @Test
    public void testCreateFlashCardStack(){
        when(studyPageRepository.findByIdAndUser(anyLong(), any())).thenReturn(studyPage);
        when(flashCardStackRepository.findByTitleAndMadeBy(anyString(), any(StudyPage.class))).thenReturn(null);
        when(flashCardStackRepository.save(any(FlashCardStack.class))).thenReturn(updated);
        FlashCardStack result = flashCardStackService.createStack(updated, studyPage.getId());
        assertEquals(result.getTitle(), "Bloop");

    }

    @Test
    public void testDeleteFlashCardStack(){
        MyUserDetails myUserDetails = (MyUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        StudyPage studyPage1 = myUserDetails.getUser().getStudyPages().get(0);
        when(flashCardStackRepository.findById(anyLong())).thenReturn(studyPage1.getFlashcardStacks().stream().findFirst());

        FlashCardStack result = flashCardStackService.deleteStack(1L);
        assertEquals(result.getTitle(), "French Fun");

    }

    private void loginUser() {

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken("test@test", "1111");

        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

    }

    
}
