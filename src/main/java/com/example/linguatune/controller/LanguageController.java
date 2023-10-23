package com.example.linguatune.controller;

import com.example.linguatune.model.Language;
import com.example.linguatune.service.LanguageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Optional;

@RestController
@RequestMapping("/api/languages")
public class LanguageController {

    @Autowired
    private LanguageService languageService;

    static HashMap<String, Object> result = new HashMap<>();
@GetMapping(path = "/{id}")
public ResponseEntity<?> getLanguageById(@PathVariable(value = "id") Long id){
    Optional<Language> optionalLanguage = Optional.ofNullable(languageService.getLanguageById(id));
    if(optionalLanguage.isPresent()){
        result.put("message", "Success");
        result.put("data", optionalLanguage.get());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    result.put("message", "Language with id " +  id + " not found");
    return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
}

    @GetMapping(path = "/")
    public ResponseEntity<?> getAllLanguages(){

            result.put("message", "Success");
            result.put("data", languageService.getLanguages());
            return new ResponseEntity<>(result, HttpStatus.OK);

    }


}
