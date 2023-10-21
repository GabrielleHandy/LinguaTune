package com.example.linguatune.serviceTests;

import com.example.linguatune.exceptions.InformationNotFoundException;
import com.example.linguatune.model.Language;
import com.example.linguatune.model.Song;
import com.example.linguatune.model.Translation;
import com.example.linguatune.repository.TranslationRepository;
import com.example.linguatune.service.TranslationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TranslationTests {

    @InjectMocks
    TranslationService translationService;

    @Mock
    TranslationRepository translationRepository;

    private Translation translation;

    @BeforeEach
    public void setUp(){
        translationService.setTranslationRepository(translationRepository);
        translation =new Translation(6L,new Language(), new Song(), "..\\LinguaTune\\src\\main\\java\\com\\example\\linguatune\\translations\\english\\1BYfviBeZuGy7SCFg4mBt9.json");

    }

    @Test
    public void testGetTranslation(){
        when(translationRepository.findById(anyLong())).thenReturn(Optional.of(translation));
        Translation result = translationService.getTranslation(6L);
        assertEquals(result.getId(), translation.getId());
    }

    @Test
    public void testGetTranslationFail(){
        when(translationRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(InformationNotFoundException.class, ()->{
            translationService.getTranslation(6L);

        });


    }

}
