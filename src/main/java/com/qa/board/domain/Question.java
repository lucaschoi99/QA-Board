package com.qa.board.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import static jakarta.persistence.CascadeType.*;
import static jakarta.persistence.GenerationType.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Question {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "question", cascade = ALL)
    private List<Answer> answerList;

    @Column(length = 200)
    @NotBlank // TODO: NotBlank Exception 만들기
    private String title;

    @Column(columnDefinition = "TEXT")
    @NotBlank // TODO: NotBlank Exception 만들기
    private String content;

    @ManyToOne
    private SiteUser author;

    @OneToMany(mappedBy = "question", cascade = ALL)
    private Set<QuestionLikes> questionLikes;

    @OneToMany(mappedBy = "questionCount", cascade = ALL)
    private Set<QuestionCount> questionCounts;

    @CreatedDate
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime lastModified;

    @Builder
    public Question(String title, String content, SiteUser author) {
        this.title = title;
        this.content = content;
        this.author = author;
    }

    public void edit(String title, String content) {
        this.title = title;
        this.content = content;
    }

    // 연관관계 메서드
    public void addLikes(QuestionLikes questionLikes) {
        this.questionLikes.add(questionLikes);
        questionLikes.setQuestion(this);
    }

    public void addCount(QuestionCount questionCount) {
        this.questionCounts.add(questionCount);
        questionCount.setQuestionCount(this);
    }
}
