package com.example.linguatune.repository;

import com.example.linguatune.model.Song;
import com.example.linguatune.model.Translation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TranslationRepository extends JpaRepository<Translation, Long> {
    Translation findByTranslatedSong(Song translatedSong);
}
