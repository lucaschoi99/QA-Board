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
public class QuestionCount {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = LAZY)
    private Question questionCount;

    @ManyToOne(fetch = LAZY)
    private SiteUser userCount;

    // 생성 메서드
    public static QuestionCount create(Question question, SiteUser user) {
        QuestionCount questionCount = new QuestionCount();
        question.addCount(questionCount);
        user.addCount(questionCount);
        return questionCount;
    }
}

