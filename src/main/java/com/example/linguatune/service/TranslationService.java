package com.example.linguatune.service;

import com.example.linguatune.exceptions.InformationNotFoundException;
import com.example.linguatune.model.Song;
import com.example.linguatune.model.Translation;
import com.example.linguatune.repository.SongRepository;
import com.example.linguatune.repository.TranslationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TranslationService {

    private TranslationRepository translationRepository;
    private SongRepository songRepository;
    @Autowired
    public void setTranslationRepository(TranslationRepository translationRepository) {
        this.translationRepository = translationRepository;
    }
    @Autowired
    public void setSongRepository(SongRepository songRepository) {
        this.songRepository = songRepository;
    }

    /**
     * Retrieve a translation by its ID.
     *
     * @param id The ID of the translation to retrieve.
     * @return The Translation with the specified ID.
     * @throws InformationNotFoundException if no Translation is found with the given ID.
     */
    public Translation getTranslation(long id) {
        Optional<Translation> translation = translationRepository.findById(id);

        if (translation.isPresent()) {
            return translation.get();
        }
        throw new InformationNotFoundException("Translation with id " + id + " can't be found");
    }

    /**
     * Get the translation of a song by its ID and the name of the translated language.
     *
     * @param songId          The ID of the song for which to retrieve the translation.
     * @return The translation of the song in the specified language.
     * @throws InformationNotFoundException if the song does not exist or if it doesn't have a translation in the specified language.
     */
    public Translation getTranslationBySong(Long songId){
        Optional<Song> songOptional = songRepository.findById(songId);
        if(songOptional.isPresent()){
            Optional<Translation> optionalTranslation = Optional.ofNullable(translationRepository.findByTranslatedSong(songOptional.get()));
            if(optionalTranslation.isPresent()){
                return optionalTranslation.get();
            }
            throw new InformationNotFoundException("Song with Id "+ songId+ " doesnt' have a translation");
        }
        throw new InformationNotFoundException("Song with Id "+ songId+ " doesn't exist");

    }
}

