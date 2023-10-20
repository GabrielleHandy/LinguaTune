package com.example.linguatune.seed;
import com.example.linguatune.model.*;
import com.example.linguatune.repository.*;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.File;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;

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
//    public void addLyrics(){
//        // Define the file path where the JSON file is located
//        try {
//            // Define the file path where the JSON file is located
//            String filePath = "C:\\Users\\gehan\\Desktop\\unit2\\Projects\\LinguaTune\\src\\main\\java\\com\\example\\linguatune\\seed\\frenchSongs.json";
//
//            // Create an ObjectMapper
//            ObjectMapper objectMapper = new ObjectMapper();
//
//            // Read the JSON file and parse it into a List of TrackInfo objects
//            JSONObject trackInfoList = objectMapper.readValue(new File(filePath), new TypeReference<>() {
//
//            });
//
//            // Update the data within the list
//
//            for ( track : (ArrayList)trackInfoList.get("data")) {
//                track = objectMapper.readValue( track, new TypeReference<Map<String,String>>(){})
//                String uri = ()track.uri  .substring(track.get("uri").indexOf("track:") + 6);
//                HttpRequest request = HttpRequest.newBuilder()
//                        .uri(URI.create("https://spotify81.p.rapidapi.com/track_lyrics?id=" + uri))
//                        .header("X-RapidAPI-Key",RAPID_KEY)
//                        .header("X-RapidAPI-Host", "spotify81.p.rapidapi.com")
//                        .method("GET", HttpRequest.BodyPublishers.noBody())
//                        .build();
//                HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
//                if(response.statusCode() == 200){
//
//
//
//
//                    track.put("Lyrics", objectMapper.writeValueAsString(response.body()));
//                }
//            }
//
//            // Convert the updated list to a JSON string
//            String updatedJsonString = objectMapper.writeValueAsString(trackInfoList);
//
//            // Write the updated JSON string back to the file
//            objectMapper.writeValue(new File("C:\\Users\\gehan\\Desktop\\unit2\\Projects\\LinguaTune\\src\\main\\java\\com\\example\\linguatune\\seed\\frenchSongsUpdated.json"), trackInfoList);
//
//            System.out.println("Data has been updated and written back to " + filePath);
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//        }
//    }


    @Override
    public void run(String... args) throws Exception {
//        if(updatedFrench.length()<1){
//            addLyrics();
//        }
        Language English = new Language();
        English.setLanguageCode("en");
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


        Song newSong = new Song();
        newSong.setTitle(danse.get("name"));
        newSong.setPictureLink(danse.get("image"));
        newSong.setOriginal_lan("fr");
        newSong.setArtist(danse.get("artist"));
        newSong.setLyrics(danse.get("lyrics"));
        newSong = songRepository.save(newSong);
//
        FlashCardStack flashCardStack = new FlashCardStack();
        flashCardStack.setTitle("Danse");
        flashCardStack.setMadeBy(testUserStudyPage);
        flashCardStack = flashCardStackRepository.save(flashCardStack);

        FlashCard flashCard = new FlashCard();
        flashCard.setFromSong(newSong);
        flashCard.setCardStack(flashCardStack);
        flashCard.setOriginalText("Danse");
        flashCard.setTranslatedText("Dance");
        flashRepository.save(flashCard);
    }
}
