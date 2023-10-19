package com.example.linguatune.repository;

import com.example.linguatune.model.FlashCardStack;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FlashCardStackRepository  extends JpaRepository<FlashCardStack, Long> {
}
