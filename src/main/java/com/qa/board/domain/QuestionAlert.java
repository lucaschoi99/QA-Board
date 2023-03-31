package com.qa.board.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class QuestionAlert {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @ManyToOne
    private SiteUser user;

    private String message;

    public static QuestionAlert createMessage(SiteUser likes, SiteUser liked, String title) {
        QuestionAlert questionAlert = new QuestionAlert();
        liked.addAlert(questionAlert);
        String created = likes.getUsername() + "님이 " + questionAlert.user.getUsername() + "님의 " + title + " 질문 글을 좋아합니다.❣️";
        questionAlert.setMessage(created);

        return questionAlert;
    }

    public static QuestionAlert alertAuthor(SiteUser commented, SiteUser author, String title) {
        QuestionAlert questionAlert = new QuestionAlert();
        author.addAlert(questionAlert);
        String alert = commented.getUsername() + "님이 " + questionAlert.user.getUsername() + "님의 " + title + " 글에 답변을 남기셨습니다.";
        questionAlert.setMessage(alert);

        return questionAlert;
    }



}
