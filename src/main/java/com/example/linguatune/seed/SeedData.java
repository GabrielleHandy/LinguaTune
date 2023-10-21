package com.example.linguatune.seed;

import com.example.linguatune.model.*;
import com.example.linguatune.repository.*;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.HashMap;
import java.util.List;

@Component
public class SeedData implements CommandLineRunner {
    private final UserRepository userRepository;

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
        English.setLanguageCode("eng");
        English.setName("English");
        English = languageRepository.save(English);

        Language Spanish = new Language();
        Spanish.setLanguageCode("spa");
        Spanish.setName("Spanish");
        Spanish=languageRepository.save(Spanish);

        Language French = new Language();
        French.setLanguageCode("fr");
        French.setName("French");
        French = languageRepository.save(French);


        User testUser = new User();
        testUser.setNativeLanguage(English);
        testUser.setPassword("111");
        testUser.setUserName("LanguageLover");
        testUser.setEmailAddress("test@test.com");
        testUser = userRepository.save(testUser);

        StudyPage testUserStudyPage = new StudyPage();
        testUserStudyPage.setUser(testUser);
        testUserStudyPage.setLanguage(French);
        testUserStudyPage = studyPageRepository.save(testUserStudyPage);


        HashMap<String, String> danse = new HashMap<>();
        danse.put("name", "Dernière danse");
        danse.put("lyrics", "src/main/java/com/example/linguatune/lyrics/65uoaqX5qcjXZRheAj1qQT.json");
        danse.put("artist", "Indila");
        danse.put("image", "https://i.scdn.co/image/ab67616d0000b2734ae8ff731c49965bf2083405");

        List<Song> songs = objectMapper.readValue(new File("..\\LinguaTune\\src\\main\\java\\com\\example\\linguatune\\seed\\songs.json"), new TypeReference<List<Song>>(){});

        songs = songRepository.saveAll(songs);
        for(Song song: songs){
            Translation engTrans = new Translation();
            engTrans.setTranslation_lan(English);
            engTrans.setTranslatedSong(song);
            engTrans.setLines("..\\LinguaTune\\src\\main\\java\\com\\example\\linguatune\\translations\\english\\" + song.getUri()+ ".json");
            translationRepository.save(engTrans);
        }


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
}
