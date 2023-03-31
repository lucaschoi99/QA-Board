package com.qa.board.repository;

import com.qa.board.domain.Question;
import com.qa.board.domain.QuestionLikes;
import com.qa.board.domain.SiteUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionUserRepository extends JpaRepository<QuestionLikes, Long> {
    QuestionLikes findByQuestionAndUser(Question question, SiteUser user);
}
