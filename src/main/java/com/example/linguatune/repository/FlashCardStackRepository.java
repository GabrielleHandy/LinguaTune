package com.example.linguatune.repository;

import com.example.linguatune.model.FlashCard;
import com.example.linguatune.model.FlashCardStack;
import com.example.linguatune.model.StudyPage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FlashCardStackRepository  extends JpaRepository<FlashCardStack, Long> {
    FlashCardStack findByTitle(String s);

    FlashCardStack findByTitleAndMadeBy(String title, StudyPage madeBy);



FlashCardStack findByIdAndMadeBy(Long flashCardStackId, StudyPage optionalStudyPage);
}
