package com.example.linguatune.seed;

import com.example.linguatune.model.*;
import com.example.linguatune.repository.*;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class SeedData implements CommandLineRunner {

    private Logger logger = Logger.getLogger(SeedData.class.getName());
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;
    private final FlashCardRepository flashRepository;

    private final FlashCardStackRepository flashCardStackRepository;

    private final LanguageRepository languageRepository;

    private final StudyPageRepository studyPageRepository;
    private final SongRepository songRepository;
    private ObjectMapper objectMapper = new ObjectMapper();
    private final TranslationRepository translationRepository;
    private File updatedFrench = new File("C:\\Users\\gehan\\Desktop\\unit2\\Projects\\LinguaTune\\src\\main\\java\\com\\example\\linguatune\\seed\\frenchSongsUpdated.json");


    @Value("rapidKey")
    String RAPID_KEY;

    @Autowired
    public SeedData(UserRepository userRepository, @Lazy PasswordEncoder passwordEncoder, FlashCardRepository flashRepository, FlashCardStackRepository flashCardStackRepository, LanguageRepository languageRepository, StudyPageRepository studyPageRepository, SongRepository songRepository, TranslationRepository translationRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
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
        English.setLanguageCode("eng");
        English.setName("English");
        English = languageRepository.save(English);

        Language Spanish = new Language();
        Spanish.setLanguageCode("spa");
        Spanish.setName("Spanish");
        Spanish = languageRepository.save(Spanish);

        Language French = new Language();
        French.setLanguageCode("fr");
        French.setName("French");
        French = languageRepository.save(French);


        User testUser = new User();
        testUser.setPassword(passwordEncoder.encode("111"));
        testUser.setUserName("LanguageLover");
        testUser.setEmailAddress("test@test.com");
        testUser = userRepository.save(testUser);

        StudyPage testUserStudyPage = new StudyPage();
        testUserStudyPage.setUser(testUser);
        testUserStudyPage.setLanguage(French);
        testUserStudyPage = studyPageRepository.save(testUserStudyPage);


        List<Song> songs = seedSongs();


        seedTranslations(songs,English);


        FlashCardStack flashCardStack = new FlashCardStack();
        flashCardStack.setTitle("French Fun");
        flashCardStack.setMadeBy(testUserStudyPage);
        flashCardStack = flashCardStackRepository.save(flashCardStack);

        FlashCard flashCard = new FlashCard();
        flashCard.setFromSong(songs.get(0));
        flashCard.setCardStack(flashCardStack);
        flashCard.setOriginalText("Danse");
        flashCard.setTranslatedText("Dance");
        flashRepository.save(flashCard);
    }

    private List<Song> seedSongs() {

        List<Song> songs = new ArrayList<Song>();
        try {
            songs = objectMapper.readValue(new File("..\\LinguaTune\\src\\main\\java\\com\\example\\linguatune\\seed\\songs.json"), new TypeReference<List<Song>>() {
            });

            for (Song song : songs) {
                // Specify the path to JSON file
                ClassPathResource resource = new ClassPathResource("assests\\lyrics\\" + song.getUri() + ".json");

                // Read the JSON data from the file
                byte[] jsonData = FileCopyUtils.copyToByteArray(resource.getInputStream());

                // Convert the JSON data to a string
                String json = new String(jsonData, "UTF-8");
                song.setLyrics(json);

            }


        } catch (IOException e) {
            logger.log(Level.SEVERE, e.getMessage());

        }


        return songRepository.saveAll(songs);
    }
    private void seedTranslations(List<Song> songs, Language language){
        for(Song song : songs){

            Translation translation = new Translation();
            translation.setTranslation_lan(language);
            translation.setTranslatedSong(song);
            try {

                    // Specify the path to JSON file
                    ClassPathResource resource = new ClassPathResource("assests\\translations\\english\\" + song.getUri() + ".json");

                    // Read the JSON data from the file
                    byte[] jsonData = FileCopyUtils.copyToByteArray(resource.getInputStream());

                    // Convert the JSON data to a string
                    String json = new String(jsonData, "UTF-8");
                    translation.setLines(json);

                } catch (IOException e) {
            logger.log(Level.SEVERE, e.getMessage());

        }

            translationRepository.save(translation);

        }
    }
}
