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
    @JoinColumn(name = "studypage_id")
    private StudyPage madeBy;

    @Column
    private Long percentageCorrect;
    @OneToMany(mappedBy = "cardStack", orphanRemoval = true)
    @Column
    @JsonIgnore
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<FlashCard> flashcards;

    public FlashCardStack() {
        this.dateMade = new Date();
        this.percentageCorrect = 0L;
    }


    public FlashCardStack(Long id, String title,  StudyPage madeBy, List<FlashCard> flashcards) {
        this.id = id;
        this.title = title;
        this.dateMade = new Date();
        this.madeBy = madeBy;
        this.flashcards = flashcards;
        this.percentageCorrect = 0L;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getDateMade() {
        return dateMade;
    }

    public void setDateMade(Date dateMade) {
        this.dateMade = dateMade;
    }

    public StudyPage getMadeBy() {
        return madeBy;
    }

    public void setMadeBy(StudyPage madeBy) {
        this.madeBy = madeBy;
    }

    public List<FlashCard> getFlashcards() {
        return flashcards;
    }

    public void setFlashcards(List<FlashCard> flashcards) {
        this.flashcards = flashcards;
    }

    public Long getPercentageCorrect() {
        return percentageCorrect;
    }

    public void setPercentageCorrect(Long percentageCorrect) {
        this.percentageCorrect = percentageCorrect;
    }

    public void calculatePercentageCorrect(){
        long correct = this.flashcards.stream().filter(FlashCard::isCorrect).count();
        this.percentageCorrect = correct>0?(correct/flashcards.size() * 100):0;
    }

    @Override
    public String toString() {
        return "FlashCardStack{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", dateMade=" + dateMade +
                ", madeBy=" + madeBy +
                ", percentageCorrect=" + percentageCorrect +
                ", flashcards=" + flashcards +
                '}';
    }
}
