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

    public void setTestLoggedInUser(User user) {
        loggedInUser = user;

    }

    public void setLoggedInUser() {
        MyUserDetails userDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        loggedInUser = userDetails.getUser();

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