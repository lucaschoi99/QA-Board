package com.qa.board.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

import static jakarta.persistence.CascadeType.*;
import static jakarta.persistence.GenerationType.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SiteUser {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(unique = true)
    private String username;

    private String password;

    @Column(unique = true)
    private String email;

    @OneToMany(mappedBy = "author", cascade = ALL)
    private List<Question> questions;

    @OneToMany(mappedBy = "author", cascade = ALL)
    private List<Answer> answers;

    @OneToMany(mappedBy = "user")
    private Set<QuestionLikes> userLikesQuestion;

    @OneToMany(mappedBy = "user")
    private Set<AnswerLikes> userLikesAnswer;

    @Builder
    public SiteUser(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    //==연관관계 메서드==//
    public void addQuestionLikes(QuestionLikes questionLikes) {
        this.userLikesQuestion.add(questionLikes);
        questionLikes.setUser(this);
    }

    public void addAnswerLikes(AnswerLikes answerLikes) {
        this.userLikesAnswer.add(answerLikes);
        answerLikes.setUser(this);
    }
}
