package com.example.linguatune.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name = "flashcards")
public class FlashCard {


    @Id
    @Column
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long id;


    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnore
    @JoinColumn(name = "flashcardstack_id")
    private FlashCardStack cardStack;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnore
    @JoinColumn(name = "song_id")
    private Song fromSong;

    @Column
    private String originalText;

    @Column
    private String translatedText;


    public FlashCard() {
    }

    public FlashCard(Long id, FlashCardStack cardStack, Song fromSong, String originalText, String translatedText) {
        this.id = id;
        this.cardStack = cardStack;
        this.fromSong = fromSong;
        this.originalText = originalText;
        this.translatedText = translatedText;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public FlashCardStack getCardStack() {
        return cardStack;
    }

    public void setCardStack(FlashCardStack cardStack) {
        this.cardStack = cardStack;
    }

    public Song getFromSong() {
        return fromSong;
    }

    public void setFromSong(Song fromSong) {
        this.fromSong = fromSong;
    }

    public String getOriginalText() {
        return originalText;
    }

    public void setOriginalText(String originalText) {
        this.originalText = originalText;
    }

    public String getTranslatedText() {
        return translatedText;
    }

    public void setTranslatedText(String translatedText) {
        this.translatedText = translatedText;
    }

    @Override
    public String toString() {
        return "FlashCard{" +
                "id=" + id +
                ", cardStack=" + cardStack +
                ", fromSong=" + fromSong.getTitle() + " by " + fromSong.getArtist() +
                ", originalText='" + originalText + '\'' +
                ", translatedText='" + translatedText + '\'' +
                '}';
    }
}
