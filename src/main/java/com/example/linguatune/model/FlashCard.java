package com.example.linguatune.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "flashcards")
public class FlashCard {


    @Id
    private Long id;




    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
