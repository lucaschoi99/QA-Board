package com.qa.board.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AnswerAlert {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @ManyToOne
    private SiteUser user;

    private String message;

    private LocalDateTime time;

    public static AnswerAlert createMessage(SiteUser likes, SiteUser liked, String comment, LocalDateTime time) {
        AnswerAlert answerAlert = new AnswerAlert();
        liked.addAnswerAlert(answerAlert);
        String created = likes.getUsername() + "님이 " + answerAlert.user.getUsername() + "님의 " + comment + " 답변을 좋아합니다.❣️";
        answerAlert.setMessage(created);
        answerAlert.setTime(time);
        return answerAlert;
    }




}
