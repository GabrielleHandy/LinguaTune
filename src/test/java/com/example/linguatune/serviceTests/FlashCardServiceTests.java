package com.example.linguatune.serviceTests;

import com.example.linguatune.exceptions.InformationNotFoundException;
import com.example.linguatune.model.*;
import com.example.linguatune.repository.FlashCardRepository;
import com.example.linguatune.repository.FlashCardStackRepository;
import com.example.linguatune.repository.StudyPageRepository;
import com.example.linguatune.service.FlashCardService;
import com.example.linguatune.service.FlashCardStackService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class FlashCardServiceTests {


    @Mock
    FlashCardRepository flashCardRepository;

    @Mock
    FlashCardStackRepository flashCardStackRepository;
    @InjectMocks
    FlashCardService flashCardService;







    private static User user;
    private StudyPage studyPage;
    private FlashCard updated;
    private FlashCard flashCard;
    private FlashCardStack flashCardStack;


    @BeforeEach
    public void setUp(){

        user = new User(1L, "LanguageLover", "test@test.com", new Language(), "111", new ArrayList<StudyPage>() );
        studyPage = new StudyPage(1L, user, new Language(), null, null);
        flashCardStack = new FlashCardStack(2L, "pop", studyPage, new ArrayList<>());
        flashCard = new FlashCard(3L, flashCardStack, new Song(), "woop", "translatedWoop");


    }

    @Test
    public void testGetFlashCardId(){
        when(flashCardRepository.findById(anyLong())).thenReturn(Optional.ofNullable(flashCard));
        assertEquals(flashCardService.findById(1L).getId(), flashCard.getId());
    }


    @Test
    public void testCreateFlashCard(){
        when(flashCardStackRepository.findById(anyLong())).thenReturn(Optional.ofNullable(flashCardStack));

        when(flashCardRepository.findByOriginalTextAndCardStack(anyString(), any())).thenReturn(null);
        when(flashCardRepository.save(any(FlashCard.class))).thenReturn(flashCard);
        FlashCard result = flashCardService.createFlashCard(flashCardStack.getId(), flashCard);
        assertEquals(result.getOriginalText(), "woop");

    }

    @Test
    public void testDeleteFlashCard(){
        when(flashCardStackRepository.findById(anyLong())).thenReturn(Optional.ofNullable(flashCardStack));
        when(flashCardRepository.findByIdAndCardStack(anyLong(), any())).thenReturn(flashCard);

        FlashCard result = flashCardService.deleteFlashCard(flashCardStack.getId(), 2L);
        assertEquals(result.getId(), flashCard.getId());

    }

    
}
