package com.example.linguatune.serviceTests;

import com.example.linguatune.exceptions.InformationNotFoundException;
import com.example.linguatune.model.*;
import com.example.linguatune.repository.SongRepository;
import com.example.linguatune.service.SongService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SongServiceTests {


    @InjectMocks
    SongService songService;

    @Mock
    SongRepository songRepository;

    private Song song;
    private StudyPage studyPage;
    @BeforeEach
    public void setUp(){
        songService.setSongRepository(songRepository);
        song = new Song(2L, "test", "woop",new ArrayList<FlashCard>(), "1234","English", "link", "flfllfl", new ArrayList<Translation>());
        studyPage = new StudyPage(2L, new User(), new Language(), new HashSet<>(), "mmmm,kkk");
    }

    @Test
    public void testGetSongById(){
        when(songRepository.findById(anyLong())).thenReturn(Optional.of(song));
        Song result = songService.getSongById(6L);
        assertEquals(result.getId(), song.getId());
    }

    @Test
    public void testGetSongFail(){
        when(songRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(InformationNotFoundException.class, ()->{
            songService.getSongById(6L);

        });


    }

    @Test
    public void testGetSongsByArtists(){
        java.util.List<Song> songList = new ArrayList<>();
        songList.add(song);
        when(songRepository.findAllByArtist(anyString())).thenReturn(songList);
        List<Song> result = songService.getSongsByArtist("woop");
        assert(result.contains(song));
    }

    @Test
    public void testGetSongsByArtistsFail(){
        when(songRepository.findAllByArtist(anyString())).thenReturn(new ArrayList<>());
        assertThrows(InformationNotFoundException.class, ()->{

            songService.getSongsByArtist("woop");
        });


    }
}
