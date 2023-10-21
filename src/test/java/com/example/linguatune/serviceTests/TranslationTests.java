package com.example.linguatune.serviceTests;

import com.example.linguatune.repository.TranslationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class TranslationTests {

    @InjectMocks
    TranslationService translationService;

    @Mock
    TranslationRepository translationRepository;


    @BeforeEach
    public void setUp(){
        translationService.setTranslationRepository(translationRepository);


    }



}
