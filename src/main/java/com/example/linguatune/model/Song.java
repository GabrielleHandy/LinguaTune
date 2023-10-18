package com.example.linguatune.model;


import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.Column;
import javax.persistence.OneToMany;
import java.util.List;
import java.util.Objects;

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
        private List<flashCard> flashCards;

        @Column
        private Object lyrics;
        @Column
        private String original_lan;

        @Column
        private List<String> availableTrans;
}
