package com.qa.board.repository;

import com.qa.board.domain.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    Question findByTitle(String title);

    Question findByTitleAndContent(String title, String content);

    List<Question> findByTitleLike(String title);
}
