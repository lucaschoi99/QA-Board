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
    private Answer likesAnswer;

    @ManyToOne(fetch = LAZY)
    private SiteUser likesUser;

}
