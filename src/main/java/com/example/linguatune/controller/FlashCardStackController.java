package com.example.linguatune.controller;

import com.example.linguatune.model.FlashCardStack;
import com.example.linguatune.service.FlashCardStackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Optional;

@RestController
@RequestMapping(path = "/api/stacks")
public class FlashCardStackController {

    @Autowired
    private FlashCardStackService flashCardStackService;

    static HashMap<String, Object> result = new HashMap<>();

    @PostMapping(path = "/create/{studyPageId}")
    public ResponseEntity<?> createFlashCardStack(@RequestBody FlashCardStack flashCardStack, @PathVariable(value = "studyPageId") Long id) {
        Optional<FlashCardStack> FlashCardStackOptional = Optional.ofNullable(flashCardStackService.createStack(flashCardStack, id));
        if (FlashCardStackOptional.isPresent()) {
            result.put("message", "Created successfully!");
            result.put("data", FlashCardStackOptional.get());
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            result.put("message", "Error creating Stack");
            return new ResponseEntity<>(result, HttpStatus.CONFLICT);
        }
    }


    @GetMapping(path = "/{id}")
    public ResponseEntity<?> getUserById(@PathVariable(value = "id") Long id) {
        Optional<FlashCardStack> flashCardStackOptional = Optional.ofNullable(flashCardStackService.findById(id));
        if (flashCardStackOptional.isPresent()) {
            result.put("message", "Success");
            result.put("data", flashCardStackOptional.get());
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            result.put("message", "Stack with id " + id + " not found");
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(path = "/delete/{stackId}")
    public ResponseEntity<?> deleteStackById(@PathVariable(value = "stackId") Long id) {
        Optional<FlashCardStack> flashCardStackOptional = Optional.ofNullable(flashCardStackService.deleteStack(id));
        if (flashCardStackOptional.isPresent()) {
            result.put("message", "successfully deleted");
            result.put("data", flashCardStackOptional.get());
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            result.put("message", "cannot delete stack with id " + id);
            return new ResponseEntity<>(result, HttpStatus.UNAUTHORIZED);
        }
    }


}
