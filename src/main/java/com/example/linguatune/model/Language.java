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
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<StudyPage> studyPages;



}
