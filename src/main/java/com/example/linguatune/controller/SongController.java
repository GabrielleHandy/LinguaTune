package com.example.linguatune.controller;

import com.example.linguatune.model.Song;
import com.example.linguatune.model.Translation;
import com.example.linguatune.service.SongService;
import com.example.linguatune.service.TranslationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/songs")
public class SongController {

    @Autowired
    private SongService songService;

    static HashMap<String, Object> result = new HashMap<>();
@GetMapping(path = "/{id}")
public ResponseEntity<?> getSongById(@PathVariable(value = "id") Long id){
    Optional<Song> optionalSong = Optional.ofNullable(songService.getSongById(id));
    if(optionalSong.isPresent()){
        result.put("message", "Success");
        result.put("data", optionalSong.get());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    result.put("message", "Song with id " +  id + " not found");
    return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
}

    @GetMapping(path = "/artist/{artist}")
    public ResponseEntity<?> getArtistSongs(@PathVariable(value = "artist") String artist){

        Optional<List<Song>> optionalSongList = Optional.ofNullable(songService.getSongsByArtist(artist));
        if(optionalSongList.isPresent()){
            result.put("message", "Success");
            result.put("data", optionalSongList.get());
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
        result.put("message", "Can't find any songs by artist " + artist );
        return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
    }


}
