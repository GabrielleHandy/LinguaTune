package com.example.linguatune.repository;

import com.example.linguatune.model.StudyPage;
import com.example.linguatune.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudyPageRepository extends JpaRepository<StudyPage, Long> {
    StudyPage findByIdAndUser(Long id, User user);

    List<StudyPage> findByUser(User loggedinUser);
}
