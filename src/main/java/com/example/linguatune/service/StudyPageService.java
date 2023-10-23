package com.example.linguatune.service;

import com.example.linguatune.exceptions.AlreadyExistException;
import com.example.linguatune.exceptions.InformationNotFoundException;
import com.example.linguatune.model.Language;
import com.example.linguatune.model.StudyPage;
import com.example.linguatune.model.User;
import com.example.linguatune.repository.LanguageRepository;
import com.example.linguatune.repository.StudyPageRepository;
import com.example.linguatune.security.MyUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class StudyPageService {

    private final StudyPageRepository studyPageRepository;
    private LanguageRepository languageRepository;
    private final UserService userService;
    private static User loggedinUser;

    @Autowired
    public StudyPageService(UserService userService, StudyPageRepository studyPageRepository, LanguageRepository languageRepository) {
        this.userService = userService;
        this.studyPageRepository = studyPageRepository;
        this.languageRepository = languageRepository;
    }

    /**
     * Set a test logged-in user for the service.
     *
     * @param user The user to set as the logged-in user for testing.
     */
    public void setTestLoggedInUser(User user) {
        loggedinUser = user;
    }

    /**
     * Set the logged-in user based on the current security context.
     * This method extracts the user information from the security context.
     */
    public void setLoggedInUser() {
        MyUserDetails userDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        loggedinUser = userDetails.getUser();
    }

    /**
     * Find a study page by its ID.
     *
     * @param id The ID of the study page to retrieve.
     * @return The StudyPage with the specified ID.
     * @throws InformationNotFoundException if no StudyPage is found with the given ID.
     */
    public StudyPage findStudyPageById(long id) {
        Optional<StudyPage> optionalStudyPage = studyPageRepository.findById(id);
        if (optionalStudyPage.isPresent()) {
            return optionalStudyPage.get();
        }
        throw new InformationNotFoundException("StudyPage with id " + id + " doesn't exist");
    }

    /**
     * Create a new study page for a specified language.
     *
     * @param language The name of the language for the study page.
     * @return The created StudyPage.
     * @throws AlreadyExistException if a study page for the same language already exists for the logged-in user.
     */
    public StudyPage createStudyPage(String language) {
        setLoggedInUser();
        Language language1 = languageRepository.findByName(language);
        if (!loggedinUser.getStudyPages().stream().anyMatch(studyPage -> studyPage.getLanguage().getName().equals(language1.getName())))
        {
            StudyPage newStudyPage = new StudyPage();
            newStudyPage.setLanguage(language1);
            newStudyPage.setUser(loggedinUser);
            return studyPageRepository.save(newStudyPage);
        }
        throw new AlreadyExistException("You already have a Study Page for " + language);
    }

    /**
     * Delete a study page by its ID.
     *
     * @param id The ID of the study page to delete.
     * @return The deleted StudyPage.
     * @throws InformationNotFoundException if no StudyPage is found with the given ID or if the logged-in user doesn't have a study page with that ID.
     */
    public StudyPage deleteStudyPage(long id) {
        setLoggedInUser();
        Optional<StudyPage> studyPage = Optional.ofNullable(studyPageRepository.findByIdAndUser(id, loggedinUser));
        if (studyPage.isPresent()) {
            studyPageRepository.delete(studyPage.get());
            return studyPage.get();
        }
        throw new InformationNotFoundException("You don't have a study page with id " + id);
    }
}
