package com.example.linguatune.serviceTests;

import com.example.linguatune.model.Language;
import com.example.linguatune.model.StudyPage;
import com.example.linguatune.repository.LanguageRepository;
import com.example.linguatune.service.LanguageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class LanguageServiceTests {


    @InjectMocks
    LanguageService languageService;

    @Mock
    LanguageRepository languageRepository;

    private Language language;
    @BeforeEach
    public void setUp(){
        language = new Language(2L, "en", "en",new ArrayList<StudyPage>(),null );
    }

    @Test
    public void testGetLanguageById(){
        when(languageRepository.findById(anyLong())).thenReturn(Optional.of(language));
        Language result = languageService.getLanguageById(6L);
        assertEquals(result.getId(), language.getId());
    }


    @Test
    public void testGetLanguageByName(){
        when(languageRepository.findByName(anyString())).thenReturn(language);
        Language result = languageService.getLanguageByName("en");
        assertEquals(result.getId(), language.getId());
    }


}
