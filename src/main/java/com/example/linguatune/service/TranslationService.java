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

    /**
     * Retrieve a translation by its ID.
     *
     * @param id The ID of the translation to retrieve.
     * @return The Translation with the specified ID.
     * @throws InformationNotFoundException if no Translation is found with the given ID.
     */
    public Translation getTranslation(long id) {
        Optional<Translation> translation = translationRepository.findById(id);

        if (translation.isPresent()) {
            return translation.get();
        }
        throw new InformationNotFoundException("Translation with id " + id + " can't be found");
    }
}

