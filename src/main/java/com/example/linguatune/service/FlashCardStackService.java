package com.example.linguatune.service;

import com.example.linguatune.exceptions.InformationNotFoundException;
import com.example.linguatune.model.FlashCardStack;
import com.example.linguatune.model.User;
import com.example.linguatune.repository.FlashCardRepository;
import com.example.linguatune.repository.FlashCardStackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FlashCardStackService {
    private FlashCardStackRepository flashCardStackRepository;

    private User loggedInUser;

    @Autowired
    public void setFlashCardStackRepository(FlashCardStackRepository flashCardStackRepository) {
        this.flashCardStackRepository = flashCardStackRepository;
    }

    public FlashCardStack findById(long l) {
        Optional<FlashCardStack> optionalFlashCardStack = flashCardStackRepository.findById(l);

        if(optionalFlashCardStack.isPresent()){
            return optionalFlashCardStack.get();
        }
        throw new InformationNotFoundException("Couldn't find FlashCardStack with Id " + l);
    }

    public FlashCardStack findByTitle(String title) {
        Optional<FlashCardStack> optionalFlashCardStack = Optional.ofNullable(flashCardStackRepository.findByTitle(title));
        if(optionalFlashCardStack.isPresent()){
            return optionalFlashCardStack.get();
        }
        throw new InformationNotFoundException("FlashCardStack with title " + title);
    }
}
