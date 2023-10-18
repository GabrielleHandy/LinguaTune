package com.example.linguatune.model;

import javax.persistence.*;

@Entity
@Table(name = "flashcardstacks")
public class FlashCardStack {
    @Id
    @Column
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long id;

}
