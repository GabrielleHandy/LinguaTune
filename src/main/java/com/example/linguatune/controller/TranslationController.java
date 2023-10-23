package com.example.linguatune.controller;

import com.example.linguatune.model.Translation;
import com.example.linguatune.service.TranslationService;
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
@RequestMapping("/api/translations")
public class TranslationController {

    @Autowired
    private TranslationService translationService;

    static HashMap<String, Object> result = new HashMap<>();
@GetMapping(path = "/{id}")
public ResponseEntity<?> getTranslationById(@PathVariable(value = "id") Long id){
    Optional<Translation> optionalTranslation = Optional.ofNullable(translationService.getTranslation(id));
    if(optionalTranslation.isPresent()){
        result.put("message", "Success");
        result.put("data", optionalTranslation.get());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    result.put("message", "Translation with id " +  id + " not found");
    return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
}

    @GetMapping(path = "/song/{id}")
    public ResponseEntity<?> getTranslationBySongId(@PathVariable(value = "id") Long id){
        Optional<Translation> optionalTranslation = Optional.ofNullable(translationService.getTranslationBySong(id));
        if(optionalTranslation.isPresent()){
            result.put("message", "Success");
            result.put("data", optionalTranslation.get());
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
        result.put("message", "Translation for song with id " +  id + " not found");
        return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
    }


}
