package com.example.linguatune.serviceTests;

import com.example.linguatune.model.*;
import com.example.linguatune.repository.FlashCardRepository;
import com.example.linguatune.repository.FlashCardStackRepository;
import com.example.linguatune.security.MyUserDetails;
import com.example.linguatune.service.FlashCardService;
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
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class FlashCardServiceTests {


    @Mock
    FlashCardRepository flashCardRepository;

    @Mock
    FlashCardStackRepository flashCardStackRepository;
    @InjectMocks
    FlashCardService flashCardService;




    @Autowired
    AuthenticationManager authenticationManager;


    private static User user;
    private StudyPage studyPage;
    private FlashCard updated;
    private FlashCard flashCard;
    private FlashCardStack flashCardStack;


    @BeforeEach
    public void setUp(){

        user = new User(1L, "LanguageLover", "test@test.com", "111", new ArrayList<StudyPage>() );
        studyPage = new StudyPage(1L, user, new Language(), null, null);
        flashCardStack = new FlashCardStack(2L, "pop", studyPage, new ArrayList<>());
        flashCard = new FlashCard(3L, flashCardStack, new Song(), "woop", "translatedWoop");

        loginUser();

    }

    @Test
    public void testGetFlashCardId(){
        when(flashCardRepository.findById(anyLong())).thenReturn(Optional.ofNullable(flashCard));
        assertEquals(flashCardService.findById(1L).getId(), flashCard.getId());
    }


    @Test
    public void testCreateFlashCard(){
        MyUserDetails myUserDetails = (MyUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        FlashCardStack stack = myUserDetails.getUser().getStudyPages().get(0).getFlashcardStacks().stream().findFirst().get();

        when(flashCardStackRepository.findById(anyLong())).thenReturn(Optional.of(stack));


        when(flashCardRepository.save(any(FlashCard.class))).thenReturn(flashCard);
        FlashCard result = flashCardService.createFlashCard(flashCardStack.getId(), flashCard);
        assertEquals(result.getOriginalText(), "woop");

    }

    @Test
    public void testDeleteFlashCard(){

        MyUserDetails myUserDetails = (MyUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        FlashCardStack stack = myUserDetails.getUser().getStudyPages().get(0).getFlashcardStacks().stream().findFirst().get();


        when(flashCardRepository.findById(anyLong())).thenReturn(Optional.ofNullable(stack.getFlashcards().get(0)));


        FlashCard result = flashCardService.deleteFlashCard(2L);
        assertEquals(result.getId(), stack.getFlashcards().get(0).getId());

    }

    private void loginUser() {

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken("test@test", "1111");

        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

    }

    
}
