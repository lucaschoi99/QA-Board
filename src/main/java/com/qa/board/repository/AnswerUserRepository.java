package com.qa.board.repository;

import com.qa.board.domain.Answer;
import com.qa.board.domain.AnswerLikes;
import com.qa.board.domain.SiteUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnswerUserRepository extends JpaRepository<AnswerLikes, Long> {
    AnswerLikes findByAnswerAndUser(Answer answer, SiteUser user);
}
