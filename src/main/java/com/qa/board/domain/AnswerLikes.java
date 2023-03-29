package com.qa.board.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AnswerLikes {

    @Id
    @GeneratedValue
    @Column(name = "answer_likes_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    private Answer answer;

    @ManyToOne(fetch = LAZY)
    private SiteUser user;

    // 생성 메서드
    public static AnswerLikes create(Answer answer, SiteUser user) {
        AnswerLikes answerLikes = new AnswerLikes();
        answer.addLikes(answerLikes);
        user.addAnswerLikes(answerLikes);
        return answerLikes;
    }

}
