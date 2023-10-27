package com.example.linguatune.repository;

import com.example.linguatune.model.Song;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SongRepository extends JpaRepository<Song, Long> {

    List<Song> findAllByArtist(String s);

    List<Song> findByOriginalLan(String lan);
}
