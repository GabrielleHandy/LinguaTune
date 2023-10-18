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



}