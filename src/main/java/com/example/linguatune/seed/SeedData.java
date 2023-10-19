package com.example.linguatune.seed;
import com.example.linguatune.model.Language;
import com.example.linguatune.model.User;
import com.example.linguatune.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class SeedData implements CommandLineRunner {
    private final UserRepository userRepository;

    private final FlashCardRepository flashRepository;

    private final FlashCardStackRepository flashCardStackRepository;

    private final LanguageRepository languageRepository;

    private final StudyPageRepository studyPageRepository;
    private final SongRepository songRepository;

    private final TranslationRepository translationRepository;

    @Autowired
    public SeedData(UserRepository userRepository, FlashCardRepository flashRepository, FlashCardStackRepository flashCardStackRepository, LanguageRepository languageRepository, StudyPageRepository studyPageRepository, SongRepository songRepository, TranslationRepository translationRepository) {
        this.userRepository = userRepository;
        this.flashRepository = flashRepository;
        this.flashCardStackRepository = flashCardStackRepository;
        this.languageRepository = languageRepository;
        this.studyPageRepository = studyPageRepository;
        this.songRepository = songRepository;
        this.translationRepository = translationRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        Language English = new Language();
        English.setLanguageCode("en");
        English.setName("English");
        languageRepository.saveAndFlush(English);

        Language Spanish = new Language();
        English.setLanguageCode("spa");
        English.setName("Spanish");
        languageRepository.saveAndFlush(Spanish);

        Language French = new Language();
        English.setLanguageCode("fr");
        English.setName("French");
        languageRepository.saveAndFlush(French);



    }
}
