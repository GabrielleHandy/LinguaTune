package com.example.linguatune.service;

import com.example.linguatune.exceptions.AlreadyExistException;
import com.example.linguatune.exceptions.InformationNotFoundException;
import com.example.linguatune.model.FlashCard;
import com.example.linguatune.model.FlashCardStack;
import com.example.linguatune.model.StudyPage;
import com.example.linguatune.model.User;
import com.example.linguatune.repository.FlashCardRepository;
import com.example.linguatune.repository.FlashCardStackRepository;
import com.example.linguatune.repository.StudyPageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FlashCardService {
    private FlashCardRepository flashCardRepository;
    private FlashCardStackRepository flashCardStackRepository;
    private User loggedInUser;

    @Autowired
    public void setFlashCardRepository(FlashCardRepository flashCardRepository) {
        this.flashCardRepository = flashCardRepository;
    }


    @Autowired
    public void setFlashCardStackRepository(FlashCardStackRepository flashCardStackRepository) {
        this.flashCardStackRepository = flashCardStackRepository;
    }

    public FlashCard findById(long l) {
        Optional<FlashCard> optionalFlashCard = flashCardRepository.findById(l);

        if(optionalFlashCard.isPresent()){
            return optionalFlashCard.get();
        }
        throw new InformationNotFoundException("Couldn't find FlashCard with Id " + l);
    }



    public FlashCard createFlashCard(Long flashCardStackId, FlashCard flashCard) {
        Optional<FlashCardStack> optionalFlashCardStack = flashCardStackRepository.findById(flashCardStackId);
        if(optionalFlashCardStack.isPresent()) {
            Optional<FlashCard> optionalFlashCard = Optional.ofNullable(flashCardRepository.findByOriginalTextAndCardStack(flashCard.getOriginalText(), optionalFlashCardStack.get()));
            if (optionalFlashCard.isEmpty()) {
                flashCard.setCardStack(optionalFlashCardStack.get());
                return flashCardRepository.save(flashCard);
            }
            throw new AlreadyExistException("You already have a FlashCard with  " + flashCard.getOriginalText() + "translation");
        }
        throw new InformationNotFoundException("Cant find a FlashCard Stack with id " + flashCardStackId);
    }

    public FlashCard deleteFlashCard(Long flashCardStackId, Long flashCardId) {
        Optional<FlashCardStack> optionalFlashCardStack = flashCardStackRepository.findById(flashCardStackId);
        if(optionalFlashCardStack.isPresent()) {
            Optional<FlashCard> optionalFlashCard = Optional.ofNullable(flashCardRepository.findByIdAndCardStack(flashCardId, optionalFlashCardStack.get()));
            if (optionalFlashCard.isPresent()) {
                flashCardRepository.deleteById(flashCardId);
                return optionalFlashCard.get();
            }
            throw new InformationNotFoundException("Couldn't find FlashCard with Id " + flashCardId + " in FlashCard Stack with Id " + flashCardStackId);
        }
        throw new InformationNotFoundException("Couldn't find FlashCard Stack with Id " + flashCardStackId);

    }
}