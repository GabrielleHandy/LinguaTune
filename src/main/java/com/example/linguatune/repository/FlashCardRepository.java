package com.example.linguatune.repository;

import com.example.linguatune.model.FlashCard;
import com.example.linguatune.model.FlashCardStack;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FlashCardRepository extends JpaRepository<FlashCard,Long> {
    FlashCard findByOriginalTextAndCardStack(String s, FlashCardStack flashCardStack);

    FlashCard findByIdAndCardStack(Long flashCardStackId, FlashCardStack flashCardStack);
}
