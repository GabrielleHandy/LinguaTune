package com.example.linguatune.service;

import com.example.linguatune.exceptions.InformationNotFoundException;
import com.example.linguatune.model.Translation;
import com.example.linguatune.repository.TranslationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TranslationService {

    private TranslationRepository translationRepository;

    @Autowired
    public void setTranslationRepository(TranslationRepository translationRepository) {
        this.translationRepository = translationRepository;
    }

    public Translation getTranslation(long id){
        Optional<Translation> translation = translationRepository.findById(id);

        if(translation.isPresent()){
            return translation.get();
        }
        throw new InformationNotFoundException("Translation with id " + id + " cant be found");
    }
}
