package com.qa.board.domain;

import com.fasterxml.classmate.AnnotationOverrides;
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

    @OneToMany(mappedBy = "user", cascade = ALL)
    private Set<QuestionLikes> userLikesQuestion;

    @OneToMany(mappedBy = "user", cascade = ALL)
    private Set<AnswerLikes> userLikesAnswer;

    @OneToMany(mappedBy = "userCount", cascade = ALL)
    private Set<QuestionCount> userCounts;

    @OneToMany(mappedBy = "user", cascade = ALL)
    private List<QuestionAlert> questionAlert;

    @OneToMany(mappedBy = "user", cascade = ALL)
    private List<AnswerAlert> answerAlert;

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

    public void addCount(QuestionCount questionCount) {
        this.userCounts.add(questionCount);
        questionCount.setUserCount(this);
    }

    public void addAlert(QuestionAlert questionAlert) {
        this.questionAlert.add(questionAlert);
        questionAlert.setUser(this);
    }

    public void addAnswerAlert(AnswerAlert answerAlert) {
        this.answerAlert.add(answerAlert);
        answerAlert.setUser(this);
    }
}
