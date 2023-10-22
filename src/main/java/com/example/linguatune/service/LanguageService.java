package com.example.linguatune.service;

import com.example.linguatune.exceptions.InformationNotFoundException;
import com.example.linguatune.model.Language;
import com.example.linguatune.model.Song;
import com.example.linguatune.model.User;
import com.example.linguatune.repository.LanguageRepository;
import com.example.linguatune.repository.SongRepository;
import com.example.linguatune.security.MyUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
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

    public Language getLanguageById(long l) {
        Optional<Language> optionalLanguage = languageRepository.findById(l);
        if(optionalLanguage.isPresent()){
            return optionalLanguage.get();
        }
        throw new InformationNotFoundException("Language with Id " + l + " doesn't exist");

    }

    public Language getLanguageByName(String name) {
        String nameUp = String.valueOf(name.charAt(0)).toUpperCase() + name.substring(1).toLowerCase();
        Optional<Language> languageOptional = Optional.ofNullable(languageRepository.findByName(nameUp));
        if(languageOptional.isPresent()) {
            return languageOptional.get();
        }
        throw new InformationNotFoundException("Sorry we dont support the language called " + name);

    }


}
