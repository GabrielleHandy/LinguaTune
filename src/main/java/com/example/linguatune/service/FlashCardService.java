package com.example.linguatune.service;

import com.example.linguatune.exceptions.AlreadyExistException;
import com.example.linguatune.exceptions.InformationNotFoundException;
import com.example.linguatune.model.FlashCard;
import com.example.linguatune.model.FlashCardStack;
import com.example.linguatune.model.User;
import com.example.linguatune.repository.FlashCardRepository;
import com.example.linguatune.repository.FlashCardStackRepository;
import com.example.linguatune.repository.SongRepository;
import com.example.linguatune.security.MyUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FlashCardService {
    private FlashCardRepository flashCardRepository;
    private FlashCardStackRepository flashCardStackRepository;
    private SongRepository songRepository;
    private User loggedInUser;

    @Autowired
    public void setFlashCardRepository(FlashCardRepository flashCardRepository) {
        this.flashCardRepository = flashCardRepository;
    }


    @Autowired
    public void setFlashCardStackRepository(FlashCardStackRepository flashCardStackRepository) {
        this.flashCardStackRepository = flashCardStackRepository;
    }
    @Autowired
    public void setSongRepository(SongRepository songRepository){
        this.songRepository = songRepository;
    }

    /**
     * Set the logged-in user based on the current security context.
     */
    public void setLoggedInUser() {
        MyUserDetails userDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        loggedInUser = userDetails.getUser();

    }

    /**
     * Retrieve a FlashCard by its ID.
     *
     * @param id The ID of the FlashCard to retrieve.
     * @return The FlashCard with the specified ID.
     * @throws InformationNotFoundException if no FlashCard is found with the given ID.
     */
    public FlashCard findById(long id) {
        Optional<FlashCard> optionalFlashCard = flashCardRepository.findById(id);

        if (optionalFlashCard.isPresent()) {
            return optionalFlashCard.get();
        }
        throw new InformationNotFoundException("Couldn't find FlashCard with Id " + id);
    }

    /**
     * Create a new FlashCard and associate it with a FlashCard Stack.
     *
     * @param flashCardStackId The ID of the associated FlashCard Stack.
     * @param flashCard        The FlashCard to create.
     * @return The created FlashCard.
     * @throws AlreadyExistException        if a FlashCard with the same original text already exists in the FlashCard Stack.
     * @throws InformationNotFoundException if the specified FlashCard Stack is not found.
     */
    public FlashCard createFlashCard(Long flashCardStackId, FlashCard flashCard, Long songId) {
        setLoggedInUser();
        Optional<FlashCardStack> optionalFlashCardStack = flashCardStackRepository.findById(flashCardStackId);

        if (optionalFlashCardStack.isPresent()) {
            if (optionalFlashCardStack.get().getMadeBy().getUser().getEmailAddress().equals(loggedInUser.getEmailAddress())) {

                Optional<FlashCard> optionalFlashCard = Optional.ofNullable(flashCardRepository.findByOriginalTextAndCardStack(flashCard.getOriginalText(), optionalFlashCardStack.get()));
                if (optionalFlashCard.isEmpty()) {

                    flashCard.setCardStack(optionalFlashCardStack.get());
                    flashCard.setFromSong(songRepository.findById(songId).get());
                    return flashCardRepository.save(flashCard);
                }
                throw new AlreadyExistException("You already have a FlashCard with  " + flashCard.getOriginalText() + "translation");


            }
            throw new InformationNotFoundException("Cant add a card to a stack that doesn't belong to you");

        }
        throw new InformationNotFoundException("Cant find a FlashCard Stack with id " + flashCardStackId);
    }

    /**
     * Delete a FlashCard from a FlashCard Stack.
     *
     * @param flashCardId The ID of the FlashCard to delete.
     * @return The deleted FlashCard.
     * @throws InformationNotFoundException if the specified FlashCard or FlashCard Stack is not found.
     */
    public FlashCard deleteFlashCard(Long flashCardId) {
        setLoggedInUser();
        Optional<FlashCard> optionalFlashCard = flashCardRepository.findById(flashCardId);
        if (optionalFlashCard.isPresent()) {
            if (optionalFlashCard.get().getCardStack().getMadeBy().getUser().getEmailAddress().equals(loggedInUser.getEmailAddress())) {

                flashCardRepository.deleteById(flashCardId);
                return optionalFlashCard.get();


            }

            throw new InformationNotFoundException("Cant delete a card to a stack that doesn't belong to you");

        }
        throw new InformationNotFoundException("Couldn't find FlashCard with Id " + flashCardId);
    }

    public List<FlashCard> findByCardStack(Long id) {
        Optional<FlashCardStack> optionalFlashCardStack = flashCardStackRepository.findById(id);
        if (optionalFlashCardStack.isPresent()) {
            return flashCardRepository.findByCardStack(optionalFlashCardStack.get());
        }
        throw new InformationNotFoundException("Couldn't find Stack with Id " + id);

    }
}