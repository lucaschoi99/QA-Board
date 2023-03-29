package com.qa.board.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import static jakarta.persistence.FetchType.EAGER;
import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Slf4j
public class QuestionLikes {

    @Id
    @GeneratedValue
    @Column(name = "question_likes_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    private Question question;

    @ManyToOne(fetch = LAZY)
    private SiteUser user;

    // 생성 메서드
    public static QuestionLikes create(Question question, SiteUser user) {
        QuestionLikes questionLikes = new QuestionLikes();
        question.addLikes(questionLikes);
        user.addQuestionLikes(questionLikes);
        return questionLikes;
    }
}
