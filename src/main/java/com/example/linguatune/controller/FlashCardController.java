package com.example.linguatune.controller;

import com.example.linguatune.model.FlashCard;
import com.example.linguatune.service.FlashCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Optional;

@RestController
@RequestMapping(path = "/api/flashcards")
public class FlashCardController {

    @Autowired
    private FlashCardService flashCardService;

    static HashMap<String, Object> result = new HashMap<>();



    @PostMapping(path = "/create/{stackId}")
    public ResponseEntity<?> createFlashCardRoute(@RequestBody FlashCard flashCard, @PathVariable(value = "stackId") Long id) {
        Optional<FlashCard> FlashCardOptional = Optional.ofNullable(flashCardService.createFlashCard( id, flashCard));
        if (FlashCardOptional.isPresent()) {
            result.put("message", "Created successfully!");
            result.put("data", FlashCardOptional.get());
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            result.put("message", "Error creating card");
            return new ResponseEntity<>(result, HttpStatus.CONFLICT);
        }
    }


    @GetMapping(path = "/{id}")
    public ResponseEntity<?> getFlashCardById(@PathVariable(value = "id") Long id) {
        Optional<FlashCard> flashCardOptional = Optional.ofNullable(flashCardService.findById(id));
        if (flashCardOptional.isPresent()) {
            result.put("message", "Success");
            result.put("data", flashCardOptional.get());
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            result.put("message", "Flashcard with id " + id + " not found");
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(path = "/delete/{cardId}")
    public ResponseEntity<?> deleteStackById(@PathVariable(value = "cardId") Long id) {
        Optional<FlashCard> flashCardOptional = Optional.ofNullable(flashCardService.deleteFlashCard(id));
        if (flashCardOptional.isPresent()) {
            result.put("message", "successfully deleted");
            result.put("data", flashCardOptional.get());
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            result.put("message", "cannot delete flashcard with id " + id);
            return new ResponseEntity<>(result, HttpStatus.UNAUTHORIZED);
        }
    }


}
