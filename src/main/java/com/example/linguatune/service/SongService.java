package com.example.linguatune.service;

import com.example.linguatune.exceptions.InformationNotFoundException;
import com.example.linguatune.model.Song;
import com.example.linguatune.model.User;
import com.example.linguatune.repository.SongRepository;
import com.example.linguatune.security.MyUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SongService {
    private SongRepository songRepository;
    private User loggedInUser;

    @Autowired
    public void setSongRepository(SongRepository songRepository) {
        this.songRepository = songRepository;
    }

    /**
     * Set the logged-in user for the service.
     *
     * @param user The user to set as the logged-in user.
     */
    public void setTestLoggedInUser(User user) {
        loggedInUser = user;
    }

    /**
     * Set the logged-in user based on the current security context.
     * This method extracts the user information from the security context.
     */
    public void setLoggedInUser() {
        MyUserDetails userDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        loggedInUser = userDetails.getUser();
    }

    /**
     * Retrieve a song by its ID.
     *
     * @param l The ID of the song to retrieve.
     * @return The Song with the specified ID.
     * @throws InformationNotFoundException if no Song is found with the given ID.
     */
    public Song getSongById(long l) {
        Optional<Song> optionalSong = songRepository.findById(l);
        if (optionalSong.isPresent()) {
            return optionalSong.get();
        }
        throw new InformationNotFoundException("Song with Id " + l + " doesn't exist");
    }

    /**
     * Retrieve a list of songs by a specific artist.
     *
     * @param artist The name of the artist.
     * @return A list of songs by the specified artist.
     * @throws InformationNotFoundException if no songs are found for the given artist.
     */
    public List<Song> getSongsByArtist(String artist) {
        artist = String.valueOf(artist.charAt(0)).toUpperCase() + artist.substring(1).toLowerCase();
        List<Song> songs = songRepository.findAllByArtist(artist);
        if (!songs.isEmpty()) {
            return songs;
        }
        throw new InformationNotFoundException("Can't find any songs by artist " + artist);
    }
}
