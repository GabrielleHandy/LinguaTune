package com.example.linguatune.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name = "translations")
public class Translation {
    @Id
    @Column
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnore
    @JoinColumn(name = "language_id")
    private Language translation_lan;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnore
    @JoinColumn(name = "song_id")
    private Song translatedSong;

    @Column
    private Object lines;

    public Translation() {
    }

    public Translation(Long id, Language translation_lan, Song translatedSong, Object lines) {
        this.id = id;
        this.translation_lan = translation_lan;
        this.translatedSong = translatedSong;
        this.lines = lines;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Language getTranslation_lan() {
        return translation_lan;
    }

    public void setTranslation_lan(Language translation_lan) {
        this.translation_lan = translation_lan;
    }

    public Song getTranslatedSong() {
        return translatedSong;
    }

    public void setTranslatedSong(Song translatedSong) {
        this.translatedSong = translatedSong;
    }

    public Object getLines() {
        return lines;
    }

    public void setLines(Object lines) {
        this.lines = lines;
    }

    @Override
    public String toString() {
        return "Translation{" +
                "id=" + id +
                ", translation_lan=" + translation_lan.getName() +
                ", translatedSong=" + translatedSong.getTitle() +
                '}';
    }
}
