package com.example.linguatune.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "users")
public class User {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String userName;
    @Column(unique = true)
    private String emailAddress;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "language_id")
    private Language nativeLanguage;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column
    private String password;
    @OneToMany(mappedBy = "user", orphanRemoval = true)
    @Column
    @JsonIgnore
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<StudyPage> studyPages;


    public User() {
    }

    public User(Long id, String userName, String emailAddress, Language nativeLanguage, String password, List<StudyPage> studyPages) {
        this.id = id;
        this.userName = userName;
        this.emailAddress = emailAddress;
        this.nativeLanguage = nativeLanguage;
        this.password = password;
        this.studyPages = studyPages;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public Language getNativeLanguage() {
        return nativeLanguage;
    }

    public void setNativeLanguage(Language nativeLanguage) {
        this.nativeLanguage = nativeLanguage;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<StudyPage> getStudyPages() {
        return studyPages;
    }

    public void setStudyPages(List<StudyPage> studyPages) {
        this.studyPages = studyPages;
    }


    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", emailAddress='" + emailAddress + '\'' +
                ", nativeLanguage=" + nativeLanguage +
                ", studyPages=" + studyPages +
                '}';
    }
}
