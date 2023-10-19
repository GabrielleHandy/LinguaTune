package com.example.linguatune.repository;

import com.example.linguatune.model.StudyPage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudyPageRepository extends JpaRepository<StudyPage, Long> {
}
