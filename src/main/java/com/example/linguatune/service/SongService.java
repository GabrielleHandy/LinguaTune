package com.example.linguatune.service;

import com.example.linguatune.exceptions.InformationNotFoundException;
import com.example.linguatune.model.Song;
import com.example.linguatune.model.User;
import com.example.linguatune.repository.SongRepository;
import com.example.linguatune.security.MyUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SongService {


    private SongRepository songRepository;
    private User loggedInUser;

    @Autowired
    public void setSongRepository(SongRepository songRepository) {
        this.songRepository = songRepository;
    }


    public void setTestLoggedInUser(User user) {
        loggedInUser = user;

    }

    public void setLoggedInUser() {
        MyUserDetails userDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        loggedInUser = userDetails.getUser();

    }

    public Song getSongById(long l) {
        Optional<Song> optionalSong = songRepository.findById(l);
        if(optionalSong.isPresent()){
            return optionalSong.get();
        }
        throw new InformationNotFoundException("Song with Id " + l + " doesn't exist");

    }
}
