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


    @Column
    private String originalText;

    @Column
    private String translatedText;




    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
