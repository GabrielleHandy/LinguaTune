package com.example.linguatune.model;


import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.Column;
import javax.persistence.OneToMany;
import java.util.List;
import javax.persistence.*;

    @Entity
    @Table(name = "songs")
    public class Song {
        @Id
        @Column
        @GeneratedValue(strategy =  GenerationType.IDENTITY)
        private Long id;

        @Column
        private String title;

        @Column
        private String artist;
        @OneToMany(mappedBy = "fromSong", orphanRemoval = true)
        @Column
        @LazyCollection(LazyCollectionOption.FALSE)
        private List<FlashCard> flashCards;

        @Column
        private Object lyrics;
        @Column
        private String original_lan;

        @Column
        private List<String> availableTrans;

    public Song() {
    }

    public Song(Long id, String title, String artist, List<FlashCard> flashCards, Object lyrics, String original_lan, List<String> availableTrans) {
        this.id = id;
        this.title = title;
        this.artist = artist;
        this.flashCards = flashCards;
        this.lyrics = lyrics;
        this.original_lan = original_lan;
        this.availableTrans = availableTrans;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public List<FlashCard> getFlashCards() {
        return flashCards;
    }

    public void setFlashCards(List<FlashCard> flashCards) {
        this.flashCards = flashCards;
    }

    public Object getLyrics() {
        return lyrics;
    }

    public void setLyrics(Object lyrics) {
        this.lyrics = lyrics;
    }

    public String getOriginal_lan() {
        return original_lan;
    }

    public void setOriginal_lan(String original_lan) {
        this.original_lan = original_lan;
    }

    public List<String> getAvailableTrans() {
        return availableTrans;
    }

    public void setAvailableTrans(List<String> availableTrans) {
        this.availableTrans = availableTrans;
    }


    @Override
    public String toString() {
        return "Song{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", artist='" + artist + '\'' +
                ", popularity=" + flashCards.size() + '\'' +
                ", original_lan='" + original_lan + '\'' +
                ", availableTrans=" + availableTrans +
                '}';
    }
}
