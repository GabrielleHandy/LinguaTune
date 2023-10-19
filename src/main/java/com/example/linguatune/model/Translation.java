package com.example.linguatune.model;

import javax.persistence.*;

@Entity
@Table(name = "translations")
public class Translation {
    @Id
    @Column
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long id;



}
