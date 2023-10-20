package com.example.linguatune.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "languages")
public class Language {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    private String languageCode;

    @OneToMany(mappedBy = "language", orphanRemoval = true)
    @Column
    @JsonIgnore
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<StudyPage> studyPages;

    @OneToMany(mappedBy = "original_lan", orphanRemoval = true)
    @Column
    @JsonIgnore
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Song> songs;

    @OneToMany(mappedBy = "nativeLanguage", orphanRemoval = true)
    @Column
    @JsonIgnore
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<User> users;

    @OneToMany(mappedBy = "translation_lan", orphanRemoval = true)
    @Column
    @JsonIgnore
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Translation> translations;

    public Language() {
    }

    public Language(Long id, String name, String languageCode, List<StudyPage> studyPages, List<Song> songs, List<User> users, List<Translation> translations) {
        this.id = id;
        this.name = name;
        this.languageCode = languageCode;
        this.studyPages = studyPages;
        this.songs = songs;
        this.users = users;
        this.translations = translations;
    }




    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLanguageCode() {
        return languageCode;
    }

    public void setLanguageCode(String languageCode) {
        this.languageCode = languageCode;
    }

    public List<StudyPage> getStudyPages() {
        return studyPages;
    }

    public void setStudyPages(List<StudyPage> studyPages) {
        this.studyPages = studyPages;
    }

    public List<Song> getSongs() {
        return songs;
    }

    public void setSongs(List<Song> songs) {
        this.songs = songs;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public List<Translation> getTranslations() {
        return translations;
    }

    public void setTranslations(List<Translation> translations) {
        this.translations = translations;
    }

    @Override
    public String toString() {
        return "Language{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", languageCode='" + languageCode + '\'' +
                ", studyPages=" + studyPages +
                ", songs=" + songs +
                ", users=" + users +
                ", translations=" + translations +
                '}';
    }
}
