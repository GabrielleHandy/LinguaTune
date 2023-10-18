package com.example.linguatune.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;

@Entity
@Table(name = "studypages")
public class StudyPage {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnore
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnore
    @JoinColumn(name = "language_id")
    private Language language;

    @ManyToMany
    @JoinTable(name = "studypage_flashcardstack",
            joinColumns = {@JoinColumn(name = "studypage_id")},
            inverseJoinColumns = {@JoinColumn(name = "flashcardstack_id")})
    @LazyCollection(LazyCollectionOption.FALSE)
    private Set<FlashcardStack> flashcardStacks = new HashSet<>();

    @Column
    private List<Song> recentSongs = new List<Song>();


    public StudyPage() {
    }

    public StudyPage(Long id, User user, Language language, Set<FlashcardStack> flashcardStacks, List<Song> recentSongs) {
        this.id = id;
        this.user = user;
        this.language = language;
        this.flashcardStacks = flashcardStacks;
        this.recentSongs = recentSongs;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public Set<FlashcardStack> getFlashcardStacks() {
        return flashcardStacks;
    }

    public void setFlashcardStacks(Set<FlashcardStack> flashcardStacks) {
        this.flashcardStacks = flashcardStacks;
    }

    public List<Song> getRecentSongs() {
        return recentSongs;
    }

    public void setRecentSongs(List<Song> recentSongs) {
        this.recentSongs = recentSongs;
    }

    @Override
    public String toString() {
        return "StudyPage{" +
                "id=" + id +
                ", user=" + user.getUserName() +
                ", language=" + language +
                ", flashcardStacks=" + flashcardStacks +
                ", recentSongs=" + recentSongs +
                '}';
    }
}