package com.qa.board.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;

import static jakarta.persistence.GenerationType.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Answer {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @ManyToOne
    private Question question;

    @Column(columnDefinition = "TEXT")
    @NotBlank
    private String content;

    @ManyToOne
    private SiteUser author;

    @OneToMany(mappedBy = "likesAnswer")
    private Set<AnswerLikes> answerLikes = new LinkedHashSet<>();

    @CreatedDate
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime lastModified;

    @Builder
    public Answer(Question question, String content, SiteUser author) {
        this.question = question;
        this.content = content;
        this.author = author;
    }

    public void edit(String content) {
        this.content = content;
    }

    // 연관관계 메서드
    public void addLikes(AnswerLikes answerLikes) {
        this.answerLikes.add(answerLikes);
        answerLikes.setLikesAnswer(this);
    }
}
