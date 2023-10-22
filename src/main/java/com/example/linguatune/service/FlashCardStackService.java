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
import com.example.linguatune.security.MyUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FlashCardStackService {
    private FlashCardStackRepository flashCardStackRepository;
    private StudyPageRepository studyPageRepository;
    private User loggedInUser;

    @Autowired
    public void setStudyPageRepository(StudyPageRepository studyPageRepository) {
        this.studyPageRepository = studyPageRepository;
    }

    @Autowired
    public void setFlashCardStackRepository(FlashCardStackRepository flashCardStackRepository) {
        this.flashCardStackRepository = flashCardStackRepository;
    }


    /**
     * Set the logged-in user for tests.
     *
     * @param user The user to set as the logged-in user.
     *
     */
    public void setTestLoggedInUser(User user) {
        loggedInUser = user;

    }

    /**
     * Set the logged-in user based on the current security context.
     */
    public void setLoggedInUser() {
        MyUserDetails userDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        loggedInUser = userDetails.getUser();

    }

    /**
     * Retrieve a FlashCardStack by its ID.
     *
     * @param l The ID of the FlashCardStack to retrieve.
     * @return The FlashCardStack with the specified ID.
     * @throws InformationNotFoundException if no FlashCardStack is found with the given ID.
     */
    public FlashCardStack findById(long l) {
        Optional<FlashCardStack> optionalFlashCardStack = flashCardStackRepository.findById(l);

        if(optionalFlashCardStack.isPresent()){
            return optionalFlashCardStack.get();
        }
        throw new InformationNotFoundException("Couldn't find FlashCardStack with Id " + l);
    }

    /**
     * Retrieve a FlashCardStack by its title.
     *
     * @param title The title of the FlashCardStack to retrieve.
     * @return The FlashCardStack with the specified title.
     * @throws InformationNotFoundException if no FlashCardStack is found with the given title.
     */
    public FlashCardStack findByTitle(String title) {
        Optional<FlashCardStack> optionalFlashCardStack = Optional.ofNullable(flashCardStackRepository.findByTitle(title));
        if(optionalFlashCardStack.isPresent()){
            return optionalFlashCardStack.get();
        }
        throw new InformationNotFoundException("FlashCardStack with title " + title);
    }

    /**
     * Create a new FlashCardStack associated with a study page.
     *
     * @param flashCardStack The FlashCardStack to create.
     * @param id             The ID of the associated study page.
     * @return The created FlashCardStack.
     * @throws AlreadyExistException if a FlashCardStack with the same title already exists in the study page.
     * @throws InformationNotFoundException if the specified study page is not found.
     */
    public FlashCardStack createStack(FlashCardStack flashCardStack, Long id) {
        StudyPage optionalStudyPage = studyPageRepository.findByIdAndUser(id, loggedInUser);
        if(optionalStudyPage != null) {
            Optional<FlashCardStack> optionalFlashCardStack = Optional.ofNullable(flashCardStackRepository.findByTitleAndMadeBy(flashCardStack.getTitle(), optionalStudyPage));
            if (optionalFlashCardStack.isEmpty()) {
                flashCardStack.setMadeBy(optionalStudyPage);
                return flashCardStackRepository.save(flashCardStack);
            }
            throw new AlreadyExistException("You already have a FlashCardStack with title " + flashCardStack.getTitle());
        }
        throw new InformationNotFoundException("You don't have a study Page with id " + id);
    }

    /**
     * Delete a FlashCardStack from a study page.
     *
     * @param flashCardStackId The ID of the FlashCardStack to delete.
     * @param studyPageId      The ID of the associated study page.
     * @return The deleted FlashCardStack.
     * @throws InformationNotFoundException if the specified FlashCardStack or study page is not found.
     */
    public FlashCardStack deleteStack(Long flashCardStackId, long studyPageId) {
        StudyPage optionalStudyPage = studyPageRepository.findByIdAndUser(studyPageId, loggedInUser);
        if(optionalStudyPage != null) {
            Optional<FlashCardStack> optionalFlashCardStack = Optional.ofNullable(flashCardStackRepository.findByIdAndMadeBy(flashCardStackId, optionalStudyPage));
            if (optionalFlashCardStack.isPresent()) {
                flashCardStackRepository.deleteById(flashCardStackId);
                return optionalFlashCardStack.get();
            }
            throw new InformationNotFoundException("Couldn't find FlashCardStack with Id " + flashCardStackId);
        }
        throw new InformationNotFoundException("You don't have a study Page with id " + studyPageId);

    }
}