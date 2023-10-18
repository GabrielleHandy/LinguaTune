package com.example.linguatune.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "flashcardstacks")
public class FlashCardStack {
    @Id
    @Column
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long id;

    @Column
    private String title;

    @Column
    private Date dateMade;
    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnore
    @JoinColumn(name = "studypage_id")
    private StudyPage madeBy;


    @OneToMany(mappedBy = "cardStack", orphanRemoval = true)
    @Column
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<FlashCard> flashcards;


}
