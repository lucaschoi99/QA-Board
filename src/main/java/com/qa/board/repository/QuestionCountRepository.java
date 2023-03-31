package com.qa.board.repository;

import com.qa.board.domain.Question;
import com.qa.board.domain.QuestionCount;
import com.qa.board.domain.SiteUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionCountRepository extends JpaRepository<QuestionCount, Long> {
    QuestionCount findByQuestionCountAndUserCount(Question question, SiteUser user);
}
