package com.example.linguatune.service;

import com.example.linguatune.exceptions.InformationNotFoundException;
import com.example.linguatune.model.Language;
import com.example.linguatune.repository.LanguageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LanguageService {


    private LanguageRepository languageRepository;
    @Autowired
    public void setLanguageRepository(LanguageRepository languageRepository) {
        this.languageRepository = languageRepository;
    }

    /**
     * Retrieve a language by its ID.
     *
     * @param l The ID of the language to retrieve.
     * @return The Language with the specified ID.
     * @throws InformationNotFoundException if no Language is found with the given ID.
     */
    public Language getLanguageById(long l) {
        Optional<Language> optionalLanguage = languageRepository.findById(l);
        if(optionalLanguage.isPresent()){
            return optionalLanguage.get();
        }
        throw new InformationNotFoundException("Language with Id " + l + " doesn't exist");

    }

    /**
     * Retrieve a language by its name.
     *
     * @param name The name of the language to retrieve.
     * @return The Language with the specified name (case-insensitive).
     * @throws InformationNotFoundException if no Language is found with the given name.
     */
    public Language getLanguageByName(String name) {
        String nameUp = String.valueOf(name.charAt(0)).toUpperCase() + name.substring(1).toLowerCase();
        Optional<Language> languageOptional = Optional.ofNullable(languageRepository.findByName(nameUp));
        if(languageOptional.isPresent()) {
            return languageOptional.get();
        }
        throw new InformationNotFoundException("Sorry we dont support the language called " + name);

    }


    public List<Language> getLanguages() {
        return languageRepository.findAll();
    }
}
