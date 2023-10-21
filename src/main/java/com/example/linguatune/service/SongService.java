package com.example.linguatune.service;

import com.example.linguatune.model.User;
import com.example.linguatune.repository.SongRepository;
import com.example.linguatune.security.MyUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

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
}
