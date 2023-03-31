package com.qa.board.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter
@Setter
@NoArgsConstructor
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
