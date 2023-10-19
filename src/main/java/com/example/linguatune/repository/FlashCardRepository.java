package com.example.linguatune.repository;

import com.example.linguatune.model.FlashCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FlashCardRepository extends JpaRepository<FlashCard,Long> {
}
