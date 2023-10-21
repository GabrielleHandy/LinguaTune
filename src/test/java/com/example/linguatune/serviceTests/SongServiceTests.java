package com.example.linguatune.serviceTests;

import com.example.linguatune.model.FlashCard;
import com.example.linguatune.model.Language;
import com.example.linguatune.model.Song;
import com.example.linguatune.model.Translation;
import com.example.linguatune.repository.SongRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;

@ExtendWith(MockitoExtension.class)
public class SongServiceTests {


    @InjectMocks
    SongService songService;

    @Mock
    SongRepository songRepository;

    private Song song;
    @BeforeEach
    public void setUp(){
        songService.setSongRepository(songRepository);
        song = new Song(2L, "test", "woop",new ArrayList<FlashCard>(), "1234","English", "link", new ArrayList<Translation>());
    }
}
