package com.example.linguatune.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;

@Entity
@Table(name = "users")
public class UserModel {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String userName;
    @Column(unique = true)
    private String emailAddress;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column
    private String password;
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private studyPage studyPage;

    public UserModel() {
    }

    public UserModel(Long id, String userName, String emailAddress, String password, studyPage studyPage) {
        this.id = id;
        this.userName = userName;
        this.emailAddress = emailAddress;
        this.password = password;
        this.studyPage = studyPage;
    }


}
