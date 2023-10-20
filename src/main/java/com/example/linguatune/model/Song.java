package com.example.linguatune.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
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
        @JsonIgnore
        @LazyCollection(LazyCollectionOption.FALSE)
        private List<FlashCard> flashCards;
        @Column
        private String uri;

        @Column
        private String original_lan;

        @Column
        private String pictureLink;

        @OneToMany(mappedBy = "translatedSong", orphanRemoval = true)
        @Column
        @JsonIgnore
        @LazyCollection(LazyCollectionOption.FALSE)
        private List<Translation> availableTrans;

    public Song() {
    }

        public Song(Long id, String title, String artist, List<FlashCard> flashCards, String uri, String original_lan, String pictureLink, List<Translation> availableTrans) {
            this.id = id;
            this.title = title;
            this.artist = artist;
            this.flashCards = flashCards;
            this.uri = uri;
            this.original_lan = original_lan;
            this.pictureLink = pictureLink;
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

    public String getUri() {
            return uri;
    }

    public void setUri(String uri) {
            this.uri = uri;
    }

        public String getOriginal_lan() {
        return original_lan;
    }

    public void setOriginal_lan(String original_lan) {
        this.original_lan = original_lan;
    }

    public List<Translation> getAvailableTrans() {
        return availableTrans;
    }

    public void setAvailableTrans(List<Translation> availableTrans) {
        this.availableTrans = availableTrans;
    }



    public String getPictureLink() {
        return pictureLink;
    }

    public void setPictureLink(String pictureLink) {
        this.pictureLink = pictureLink;
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
